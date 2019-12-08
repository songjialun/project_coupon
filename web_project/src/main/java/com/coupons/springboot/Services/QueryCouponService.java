package com.coupons.springboot.Services;

import com.coupons.springboot.Entities.CouponCustomerEntity;
import com.coupons.springboot.Entities.CouponSalerEntity;
import com.coupons.springboot.Entities.UserEntity;
import com.coupons.springboot.Repositories.CouponCustomerRepository;
import com.coupons.springboot.Repositories.CouponSalerRepository;
import com.coupons.springboot.Repositories.UserRepository;
import com.coupons.springboot.Tools.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QueryCouponService {
    // TODO:获取优惠券信息服务类

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponSalerRepository couponSalerRepository;

    @Autowired
    private CouponCustomerRepository couponCustomerRepository;

    // 每一页包含的记录数
    private static int NUMBER_PER_PAGE = 20;


    public Map<String,Object> QueryCoupon(String userName,String token,int page) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("errMsg", "") ;
        map.put("status", HttpStatus.OK) ;  // 默认http码为200

        if(TokenUtil.validTokenByUsername(token,userName)){
            // 若url中的username与头部authorization指定的用户名一致，则返回该用户自己/商家剩余的优惠券信息

            // 先判断是商家还是顾客
            List<UserEntity> u = userRepository.findByUsername(userName);
            System.out.println("u.size = "+u.size());
            if(u==null || u.size()==0){
                map.put("errMsg","查询的用户不存在");
                map.put("status",HttpStatus.BAD_REQUEST); // 返回http码 400
                return map;
            }
            else{
                List<Map<String,Object>> data = new ArrayList<>();
                if(u.get(0).getKind().equals("saler")){
                    // 查询商家剩余的优惠券信息，所以从coupon_saler_info表中查询数据
                    List<CouponSalerEntity> couponList = couponSalerRepository.findAllBySalerName(userName);
                    if(couponList.size()==0){
                        map.put("errMsg","查询结果为空");
                        map.put("status",HttpStatus.NO_CONTENT); // 返回http码 204
                    }

                    // 原本想用SQL语句实现分页查询的，但是试了一下好像不行。由于时间比较紧，这里直接用代码控制页码。
                    int start = (page-1)*NUMBER_PER_PAGE;
                    int end = page*NUMBER_PER_PAGE;
                    for (int i = start; i < end; i++) {
                        if(i>=couponList.size()){
                            break;
                        }
                        // 整理格式
                        Map<String,Object> item = new HashMap<>();
                        item.put("name",couponList.get(i).getCouponName());
                        item.put("amount",couponList.get(i).getTotalAmount());
                        item.put("left",couponList.get(i).getLeftAmount());
                        item.put("stock",couponList.get(i).getStock());
                        item.put("description",couponList.get(i).getDescription());
                        data.add(item);
                    }
                }else {
                    // 查询顾客自己已经抢到的优惠券信息，所以从coupon_customer_info表中查询数据
                    List<CouponCustomerEntity> couponCustomerList = couponCustomerRepository.findAllByCustomerName(userName);

                    if(couponCustomerList.size()==0){
                        map.put("errMsg","查询结果为空");
                        map.put("status",HttpStatus.NO_CONTENT); // 返回http码 204
                    }
                    int start = (page-1)*NUMBER_PER_PAGE;
                    int end = page*NUMBER_PER_PAGE;

                    for (int i = start; i < end; i++) {
                        if(i>=couponCustomerList.size()){
                            break;
                        }
                        // 整理格式
                        Map<String,Object> item = new HashMap<>();
                        item.put("name",couponCustomerList.get(i).getCouponName());
                        item.put("stock",couponCustomerList.get(i).getStock());
                        item.put("description",couponCustomerList.get(i).getDescription());
                        // 看API文档上的意思，查询顾客自己已经抢到的优惠券信息好像不需要返回amount 和 left的值
                        // 但是说明文档上的意思又是这两个值恒定为1
                        // 这里还是将这两个值设为1返回给客户端，客户端读不读取这两个数据由客户端决定
                        item.put("amount",1);
                        item.put("left",1);
                        data.add(item);
                    }
                }
                map.put("data",data);
            }

        }else{
            List<UserEntity> u = userRepository.findByUsername(userName);
            if(u==null || u.size()==0){
                map.put("errMsg","查询的用户不存在，认证失败");
                map.put("status",HttpStatus.UNAUTHORIZED); // 返回http码 401
                return map;
            }
            else{

                if(u.get(0).getKind().equals("saler")){
                    // 若不一致且url指定的用户名身份为商家，则获取该商家的优惠券余量
                    // 查询商家，所以从coupon_saler_info表中查询数据
                    List<CouponSalerEntity> couponList = couponSalerRepository.findAllBySalerName(userName);
                    List<Map<String,Object>> data = new ArrayList<>();

                    int start = (page-1)*NUMBER_PER_PAGE;
                    int end = page*NUMBER_PER_PAGE;
                    for (int i = start; i < end; i++) {
                        if(i>=couponList.size()){
                            break;
                        }
                        // 整理格式
                        Map<String,Object> item = new HashMap<>();
                        item.put("name",couponList.get(i).getCouponName());
                        item.put("amount",couponList.get(i).getTotalAmount());
                        item.put("left",couponList.get(i).getLeftAmount());
                        item.put("stock",couponList.get(i).getStock());
                        item.put("description",couponList.get(i).getDescription());
                        data.add(item);
                    }
                    map.put("data",data);
                }else {
                    // 若不一致且url指定的用户名身份为普通用户，则返回认证失败的错误
                    map.put("errMsg","试图访问其他顾客的优惠券列表，认证失败");
                    map.put("status",HttpStatus.UNAUTHORIZED); // 返回http码 401
                    return map;
                }
            }
        }

        return map;
    }
}

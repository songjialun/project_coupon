package com.coupons.springboot.Services;

import com.coupons.springboot.Entities.CouponSalerEntity;
import com.coupons.springboot.Repositories.CouponSalerRepository;
import com.coupons.springboot.Tools.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddCouponService {
    // TODO: 商家新增优惠券服务类

    @Autowired
    private CouponSalerRepository couponSalerRepository;

    public Map<String,Object> AddCoupon(@NonNull CouponSalerEntity couponSalerEntity, String token) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("errMsg", "") ;
        map.put("status", HttpStatus.OK) ;

        String couponName = couponSalerEntity.getCouponName();
        String salerName = couponSalerEntity.getSalerName();

        try{
            if(TokenUtil.validTokenByUsername(token,salerName)){
                System.out.println("token 认证通过");

                // 获取数据库中是否已经有商家添加该优惠券的记录
                List<CouponSalerEntity> c = couponSalerRepository.findAllByCouponNameAndSalerName(couponName,salerName);

                if (c == null || c.size() == 0){
                    // 若为0，新增该发放优惠券的记录
                    couponSalerRepository.save(couponSalerEntity);
                    System.out.println("若为0，新增该发放优惠券的记录");
                }else{
                    // 若不为0，则修改原记录中发放优惠券的totalAmount和leftAmount，加上这次请求发放的优惠券数量
                    int addAmount = couponSalerEntity.getTotalAmount(); // 这次请求发放的优惠券数量

                    long recordId = c.get(0).getId();
                    couponSalerRepository.addCouponAmount(addAmount,recordId);
                    System.out.println("则修改原记录中发放优惠券的totalAmount和leftAmount，加上这次请求发放的优惠券数量 : "+addAmount);
                }
            }else{
                System.out.println("token 和 用户名 不匹配");
                map.put("status", HttpStatus.UNAUTHORIZED) ;
                map.put("errMsg","Authorization 认证失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("status", HttpStatus.NOT_IMPLEMENTED);  // 添加过程发现异常，按API文档说的，返回501错误码
            map.put("errMsg","添加优惠券失败，服务器出错");

            //  ------------------- 回滚数据库操作 ------------------
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return  map;
    }
}

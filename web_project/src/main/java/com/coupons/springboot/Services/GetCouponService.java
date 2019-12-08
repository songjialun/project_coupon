package com.coupons.springboot.Services;

import com.coupons.springboot.Entities.CouponCustomerEntity;
import com.coupons.springboot.Entities.CouponSalerEntity;
import com.coupons.springboot.Repositories.CouponCustomerRepository;
import com.coupons.springboot.Repositories.CouponSalerRepository;
import com.coupons.springboot.Tools.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetCouponService {
    // TODO:抢优惠券服务类

    @Autowired
    private CouponCustomerRepository couponCustomerRepository;

    @Autowired
    private CouponSalerRepository couponSalerRepository;

    @Transactional
    public Map<String,Object> GetCoupon(String salerName,String couponName, String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errMsg", "");
        map.put("status", HttpStatus.OK);  // 默认http码为200

        try{
            String customerName = TokenUtil.parseNameFromJwt(token);
            String userKind = TokenUtil.parseKindFromJwt(token);
            if(customerName.isEmpty() || !userKind.equals("customer")){
                // 若从token中解析不出用户名 ，或者解析出用户类型不是顾客, 返回认证出错的提示
                map.put("errMsg","认证失败");
                map.put("status",HttpStatus.UNAUTHORIZED);      // http码401
                return map;
            }else{
                // 先判断该顾客是否已经抢到了该优惠券
                int count = couponCustomerRepository.countAllByCouponNameAndCustomerName(couponName,customerName);
                if(count>0){
                    // 该顾客已经抢到了该优惠券，无法再抢
                    map.put("errMsg","已经抢到了该优惠券，每人限抢1张");
                    map.put("status",HttpStatus.NO_CONTENT);  // http码204
                    return map;

                }else{
                    // 该顾客还没有抢过该优惠券，尝试去抢
                    // 先判断是否存在该商家发放该优惠券的记录
                    List<CouponSalerEntity> c = couponSalerRepository.findAllByCouponNameAndSalerName(couponName,salerName);
                    if (c == null || c.size() == 0){
                        // 若为0，指定商家没有发放该优惠券。返回错误信息
                        map.put("errMsg","商家未发放该优惠券");
                        map.put("status",HttpStatus.NO_CONTENT);      // http码204
                        return map;
                    }else{
                        CouponSalerEntity couponSalerEntity = c.get(0);
                        if(couponSalerEntity.getLeftAmount() > 0){
                            // 优惠券剩余数量大于0,直接-1
                            long recordId = couponSalerEntity.getId();
                            int result = couponSalerRepository.minusCouponAmount(1,recordId);
                            if(result>0){
                                // 在顾客抢票记录表coupon_customer_info中，新增抢票记录
                                CouponCustomerEntity newEntity = new CouponCustomerEntity();
                                newEntity.setCouponName(couponSalerEntity.getCouponName());
                                newEntity.setCustomerName(customerName);
                                newEntity.setStock(couponSalerEntity.getStock());
                                newEntity.setDescription(couponSalerEntity.getDescription());
                                couponCustomerRepository.save(newEntity);

                                map.put("errMsg","抢到优惠券");
                                map.put("status",HttpStatus.CREATED);      // http码201
                                return map;

                            }else{
                                map.put("errMsg","优惠券已被抢空");
                                map.put("status",HttpStatus.NO_CONTENT);      // http码204
                                return map;
                            }
                        }else{
                            // 优惠券数量等于0，已经被抢完
                            map.put("errMsg","优惠券已被抢空");
                            map.put("status",HttpStatus.NO_CONTENT);      // http码204
                            return map;
                        }
                    }
                }
            }
        }catch (Exception e){
            map.put("errMsg","抢券失败，服务器出错");
            map.put("status",HttpStatus.NOT_IMPLEMENTED);  // http码500

            /*
                因为抢优惠券先后对coupon_saler_info 和 coupon_customer_info  两张表进行update和insert操作。
                只要其中有一条操作失败，意味着这次抢券失败，应该统一回滚数据库
             */
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return map;
    }

}

package com.coupons.springboot.Repositories;

import com.coupons.springboot.Entities.CouponCustomerEntity;
import com.coupons.springboot.Entities.CouponSalerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponCustomerRepository extends JpaRepository<CouponCustomerEntity,Integer> {
    // TODO: 对表coupon_customer进行操作，其中存储的是顾客领取到优惠券的记录


    /*
        根据优惠券名称和顾客名称查询记录的数目
     */
    int countAllByCouponNameAndCustomerName(String couponName, String customerName);


    /*
        根据顾客名称查查询
     */
    List<CouponCustomerEntity> findAllByCustomerName(String customerName);

}

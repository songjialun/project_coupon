package com.coupons.springboot.Repositories;

import com.coupons.springboot.Entities.CouponSalerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface CouponSalerRepository extends JpaRepository<CouponSalerEntity, Integer> {
    // TODO: 对表coupon_saler进行操作，其中存储的是商家发放优惠券的记录

    //根据优惠券名称和商家名称查询
    List<CouponSalerEntity> findAllByCouponNameAndSalerName(String couponName, String salerName);



    //根据商家名称查查询
    List<CouponSalerEntity> findAllBySalerName(String salerName);


    @Modifying
    @Transactional
    @Query(value = "select * from CouponSalerEntity c  where c.couponName = ?1 and c.salerName = ?2 \n#pageable\n",
        nativeQuery = true)
    List<CouponSalerEntity> findAllByCouponNameAndSalerNameAtPage(String couponName, String SalerName, Pageable pageable);


    //增加优惠券的数量
    @Modifying
    @Transactional()
    @Query("update CouponSalerEntity c set c.totalAmount = c.totalAmount + ?1 , c.leftAmount = c.leftAmount+ ?1 where c.id = ?2")
    int addCouponAmount(int addAmount, long id);



    //减少优惠券的数量
    @Modifying
    @Transactional
    @Query("update CouponSalerEntity c set c.leftAmount = c.leftAmount - ?1 where c.id = ?2")
    int minusCouponAmount(int minusAmount, long id);


}

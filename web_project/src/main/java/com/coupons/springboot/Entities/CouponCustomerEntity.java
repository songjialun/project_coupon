package com.coupons.springboot.Entities;

import javax.persistence.*;

@Entity
@Table(name = "coupon_customer_info")
public class CouponCustomerEntity {
    // TODO:定义表coupon_customer_info的字段，该表存储用户领取优惠券的情况

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;        //自增id，作主键

    private String couponName;  // 该优惠券名词

    private String customerName; // 该优惠券所属的用户名

    private int stock;          // 优惠券面额

    private String description; // 优惠券描述


    public long getId() {
        return id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

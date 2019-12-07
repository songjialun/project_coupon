package com.coupons.springboot.Entities;

import javax.persistence.*;

@Entity
@Table(name = "coupon_saler_info")
public class CouponSalerEntity {
    // TODO:定义表coupon_saler_info的字段，该表存储商家发放优惠券的情况

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;            // 自增id，作主键

    private String couponName;  // 优惠券名称

    private int stock;          // 优惠券面额

    private String salerName;   // 商家名称

    private int totalAmount;    // 商家发放该优惠券的总数

    private int leftAmount;     // 该优惠券目前剩余的数量

    private String description; // 优惠券描述信息

    public long getId() {
        return id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(int leftAmount) {
        this.leftAmount = leftAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

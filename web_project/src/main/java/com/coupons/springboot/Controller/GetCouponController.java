package com.coupons.springboot.Controller;


import com.coupons.springboot.Services.GetCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GetCouponController {
    // TODO:抢优惠券控制类

    @Autowired
    private GetCouponService getCouponService;

    @RequestMapping(value = "/api/users/{username}/coupons/{name}")
    public ResponseEntity<Map<String, Object>> Query(@PathVariable("username") String salerName, @PathVariable("name") String couponName,
                                                     @RequestHeader("Authorization") String token){


        System.out.println("请求优惠券信息！");

        // 返回的body内容
        Map<String,Object> map = getCouponService.GetCoupon(salerName,couponName,token);

        // 返回的状态码
        HttpStatus status = (HttpStatus) map.get("status");

        return new ResponseEntity<Map<String,Object>>(map, status);
    }


}

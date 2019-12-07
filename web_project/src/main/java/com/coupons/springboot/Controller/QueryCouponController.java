package com.coupons.springboot.Controller;

import com.coupons.springboot.Services.QueryCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class QueryCouponController {
    // TODO：获取优惠券信息控制类

    @Autowired
    private QueryCouponService queryCouponService;


    @RequestMapping(value = "/api/users/{username}/coupons",method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> Query(@PathVariable("username") String userName, @RequestParam("page") int page,
                                                     @RequestHeader("Authorization") String token){


        System.out.println("请求优惠券信息！");

        // 返回的body内容
        Map<String,Object> map = queryCouponService.QueryCoupon(userName,token,page);

        // 返回的状态码
        HttpStatus status = (HttpStatus) map.get("status");

        return new ResponseEntity<Map<String,Object>>(map, status);
    }

}

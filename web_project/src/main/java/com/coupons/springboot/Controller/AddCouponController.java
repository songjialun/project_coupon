package com.coupons.springboot.Controller;

import com.coupons.springboot.Entities.CouponSalerEntity;
import com.coupons.springboot.Services.AddCouponService;
import com.coupons.springboot.Tools.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AddCouponController {
    // TODO: 新增优惠券控制类

    @Autowired
    AddCouponService addCouponService;

    @RequestMapping(value = "/api/users/{username}/coupons", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> Register(@PathVariable String username, @RequestParam("name") String couponName, @RequestParam("amount") int amount,
                                                        @RequestParam("description") String description, @RequestParam("stock") int stock,
                                                        @RequestHeader("Authorization") String token){
        System.out.println("新增优惠券");

        CouponSalerEntity couponSalerEntity = new CouponSalerEntity();
        couponSalerEntity.setCouponName(couponName);
        couponSalerEntity.setStock(stock);
        couponSalerEntity.setSalerName(username);
        couponSalerEntity.setLeftAmount(amount);
        couponSalerEntity.setTotalAmount(amount);
        couponSalerEntity.setDescription(description);

        // 返回的body内容
        Map<String,Object> map = addCouponService.AddCoupon(couponSalerEntity,token);

        // 返回的状态码
        HttpStatus status = (HttpStatus) map.get("status");


        return new ResponseEntity<Map<String,Object>>(map, status);
    }
}

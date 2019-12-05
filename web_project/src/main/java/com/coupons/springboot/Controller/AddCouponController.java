package com.coupons.springboot.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AddCouponController {
    // TODO: 新增优惠券

    @RequestMapping("/api/users/{username}/coupons")
    public ResponseEntity<Map<String, Object>> Register(@PathVariable String username,@RequestParam("name") String name, @RequestParam("amount") int amount,
                                                        @RequestParam("description") String description, @RequestParam("stock") int stock){

        System.out.println("新增优惠券");
        System.out.println("username : "+username);
        System.out.println("name : "+name);
        System.out.println("amount : "+amount);
        System.out.println("description : "+description);
        System.out.println("stock : "+stock);

        Map<String,Object> map = new HashMap<String, Object>();

        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }
}

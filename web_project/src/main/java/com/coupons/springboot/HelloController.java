package com.coupons.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@ RestController
public class HelloController {

    @Value("${name}")
    private  String name;

    @Value("${age}")
    private  String age;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring Boot";
    }

    @RequestMapping("/hello2")
    public String hello2(){
        return "Hello Spring Boot , my name is " + name + " , and I'm " + age;
    }
}

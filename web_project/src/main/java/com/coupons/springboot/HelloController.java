package com.coupons.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    // TODO:测试环境是否正常

    @RequestMapping("/hello")
    public String HelloWorld(){
        return "Hello World! 配置成功";
    }


}

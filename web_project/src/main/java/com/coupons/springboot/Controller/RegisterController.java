package com.coupons.springboot.Controller;

import com.coupons.springboot.Entities.UserEntity;
import com.coupons.springboot.Services.RigisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegisterController {
    //TODO: 用户注册控制类

    @Autowired
    private RigisterService registerService;

    @RequestMapping(value = "/api/users",method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> Register(@RequestParam("username") String username, @RequestParam("password") String password,
                                                        @RequestParam("kind") String kind){

        System.out.println("用户注册");

        // 将参数打包在一个UserEntity里面，便于后续操作
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setKind(kind);

        // 返回的body内容
        Map<String,Object> map = registerService.register(user);

        // 按API文档要求，返回body，以及http状态码
        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }
}

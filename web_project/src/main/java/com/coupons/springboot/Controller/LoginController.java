package com.coupons.springboot.Controller;

import com.coupons.springboot.Entities.UserEntity;
import com.coupons.springboot.Services.LoginService;
import com.coupons.springboot.Tools.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    // TODO: 用户登录控制类

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/api/auth",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> Login(@RequestParam("username") String username, @RequestParam("password") String password){
        // 将参数打包在一个UserEntity里面，便于后续操作
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);

        // 返回的header
        HttpHeaders headers = new HttpHeaders();

        // 返回的body内容
        Map<String,Object> map = loginService.login(user);

        // 返回的状态码
        HttpStatus status;

        if(map.get("errMsg").toString().isEmpty()){
            // errMsg为空，说明登录成功
            System.out.println("登录成功");
            String token = TokenUtil.createJWT(username,map.get("kind").toString());
            TokenUtil.parseJWT(token);
            headers.add("Authorization",token);
            status = HttpStatus.OK; // 200
        }else{
            // errMsg不为空，说明登录发生错误，错误原因已经在errMsg中
            System.out.println("登录失败");
            status = HttpStatus.UNAUTHORIZED; // 401
        }

        // 按API文档要求，返回body，header，以及http状态码
        return new ResponseEntity<Map<String,Object>>(map, headers,status);
    }
}

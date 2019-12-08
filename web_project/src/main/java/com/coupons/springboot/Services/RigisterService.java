package com.coupons.springboot.Services;

import com.coupons.springboot.Entities.UserEntity;
import com.coupons.springboot.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RigisterService {
    // TODO: 用户注册服务类

    @Autowired
    private UserRepository userRepository;

    public Map<String,Object> register(@NonNull UserEntity user){
        Map<String,Object> map = new HashMap<String, Object>();

        // 根据请求中的用户名，去用户注册表user_info中查找相关数据
        List<UserEntity> u = userRepository.findByUsername(user.getUsername());
        System.out.println("u.size = "+u.size());
        if(u==null || u.size()==0){
            userRepository.save(user);  // 可用的用户名，将用户名和密码保存至user_info中，注册成功
            map.put("errMsg","");
        }
        else{
            map.put("errMsg","用户已存在");
        }
        return map;
    }
}

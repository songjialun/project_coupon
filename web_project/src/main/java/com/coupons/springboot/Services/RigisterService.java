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

        List<UserEntity> u = userRepository.findByUsername(user.getUsername());
        System.out.println("u.size = "+u.size());
        if(u==null || u.size()==0){
            userRepository.save(user);
            map.put("errMsg","");
        }
        else{
            map.put("errMsg","用户已存在");
        }
        return map;
    }
}

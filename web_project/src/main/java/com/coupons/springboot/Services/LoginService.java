package com.coupons.springboot.Services;

import com.coupons.springboot.Entities.UserEntity;
import com.coupons.springboot.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {
    // TODO: 用户登录服务类

    @Autowired
    private UserRepository userRepository;

    public Map<String,Object> login(@NonNull UserEntity user){
        Map<String,Object> map = new HashMap<String, Object>();

        // 根据请求中的用户名，去用户注册表user_info中查找相关数据
        List<UserEntity> u = userRepository.findByUsername(user.getUsername());
        if(u==null || u.size()==0){
            map.put("kind",null);
            map.put("errMsg","用户不存在");
        }
        else{
            u = userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
            if(u==null || u.size()==0){
                map.put("kind",null);
                map.put("errMsg","密码错误");
            }
            else {
                map.put("kind", u.get(0).getKind());
                map.put("errMsg", "");
            }
        }
        return map;
    }
}

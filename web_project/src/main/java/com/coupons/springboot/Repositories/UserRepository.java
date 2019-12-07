package com.coupons.springboot.Repositories;

import com.coupons.springboot.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    // TODO: 对表user_info进行操作，其中存储的是用户注册的记录

    public List<UserEntity> findByUsername(String username);
    public List<UserEntity> findByUsernameAndPassword(String username,String password);
}

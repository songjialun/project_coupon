package com.coupons.springboot.Entities;


//import lombok.Data;

import javax.persistence.*;


//@Entity
//@Table(name="user_Info")
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String username;

    private  String password;

    private  String nickname;
}

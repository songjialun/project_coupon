package com.coupons.springboot.Tools;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenUtil {
    // TODO: Token生成与解析的工具类

    // 定义token有效期，单位为毫秒
    private static final long TOKEN_EXPERIRED_TIME = 24*60*60*1000;

    // 生成和解析token使用的秘钥值
    private static final String KEY_STR = "miyao";

    public static String createJWT(String userName,String kind){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY_STR);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder().setId(userName).setSubject(kind).setIssuedAt(now).signWith(signatureAlgorithm,signingKey);
        System.out.println("token 生成----");
        long expMills = nowMills +TOKEN_EXPERIRED_TIME;
        Date exp = new Date(expMills);
        System.out.println("过期时间: " + exp.toString());
        jwtBuilder.setExpiration(exp);

        return jwtBuilder.compact();
    }

    public static boolean validTokenByUsername(String jwt, String username){
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY_STR)).parseClaimsJws(jwt).getBody();
            System.out.println("------解析token----");
            System.out.println("ID: " + claims.getId());

            /*
            检验token和用户名是否匹配
            */
            if (!claims.getId().toString().equals(username)){
                return  false;
            }
            return true;
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return false;
        }catch (SignatureException e1){
            e1.printStackTrace();
            return  false;
        }catch (MalformedJwtException e2){
            e2.printStackTrace();
            return false;
        }
    }

    public  static  String parseKindFromJwt(String jwt){
        // TODO: 从token中解析出用户类型Kind
        try{
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY_STR)).parseClaimsJws(jwt).getBody();
            return claims.getSubject().toString();
        }catch (Exception e){
            return "";
        }
    }

    public  static  String parseNameFromJwt(String jwt){
        // TODO：从token中解析出用户名userName
        try{
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY_STR)).parseClaimsJws(jwt).getBody();
            return claims.getId().toString();
        }catch (Exception e){
            return "";
        }
    }

    public  static  Object parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY_STR)).parseClaimsJws(jwt).getBody();
            System.out.println("------解析token----");
            System.out.println("ID: " + claims.getId());
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("IssuerAt:   " + claims.getIssuedAt());
            System.out.println("Expiration: " + claims.getExpiration());
            /*
            检验token是或否即将过期，如快要过期，就提前更新token。如果已经过期的，会抛出ExpiredJwtException 的异常
            */
            Long exp=claims.getExpiration().getTime(); //过期的时间
            long nowMillis = System.currentTimeMillis();//现在的时间
            Date now=new Date(nowMillis);
            System.out.println("currenTime:"+now);
            long seconds=exp-nowMillis;//剩余的时间 ，若剩余的时间小与48小时，就返回一个新的token给APP
            long days=seconds/(1000*60*60*24);
            long hour=(seconds-days*1000*60*60*24)/3600000;
            long minutes = (seconds-days*1000*60*60*24-hour*3600000) / 60000;
            long remainingSeconds = seconds % 60;
            System.out.println(seconds + " seconds is "+days+" days "+hour+" hours " + minutes + " minutes and "+ remainingSeconds + " seconds");
            if (seconds<=TOKEN_EXPERIRED_TIME){
                System.out.println("token的有效期小与24小时，请更新token！");
                return  "update";
            }
            return "success";
        }catch (ExpiredJwtException e){
            e.printStackTrace();
            return ExpiredJwtException.class.getName();
        }catch (SignatureException e1){
            e1.printStackTrace();
            return  SignatureException.class.getName();
        }catch (MalformedJwtException e2){
            e2.printStackTrace();
            return MalformedJwtException.class.getName();
        }
    }

}

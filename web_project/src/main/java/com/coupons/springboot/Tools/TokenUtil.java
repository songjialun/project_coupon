package com.coupons.springboot.Tools;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenUtil {
    // TODO: Token生成与解析的工具类
    private static final long TOKEN_EXPERIRED_TIME = 24*60*60*1000;

    public static String createJWT(String id){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("miyao");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());

        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setIssuedAt(now).signWith(signatureAlgorithm,signingKey);
        System.out.println("token 生成----");
        long expMills = nowMills +TOKEN_EXPERIRED_TIME;
        Date exp = new Date(expMills);
        System.out.println("过期时间: " + exp.toString());
        jwtBuilder.setExpiration(exp);

        return jwtBuilder.compact();
    }

    public  static  Object parseJWT(String jwt) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("miyao")).parseClaimsJws(jwt).getBody();
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

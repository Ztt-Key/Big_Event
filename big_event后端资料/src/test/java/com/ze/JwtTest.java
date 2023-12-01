package com.ze;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {


    /**
     * 生成登录token
     */
    @Test
    public void JwyTest(){
        Map<String, Object> hasmaps = new HashMap<>();
        hasmaps.put("id",1);
        hasmaps.put("user","张三");
      String token =  JWT.create()
                .withClaim("user",hasmaps)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))
                .sign(Algorithm.HMAC256("ze"));
        System.out.println(token);
    }
    /**
     * 验证登录token
     */
    @Test
    public void testpasre(){
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJleHAiOjE3MDA2NjI4OTMsInVzZXIiOnsiaWQiOjEsInVzZXIiOiLlvKDkuIkifX0." +
                "pCv-LwxfTLBdToI9PFQZi1RoBZORtwx7kBJqHkAcXPg";
        JWTVerifier ze = JWT.require(Algorithm.HMAC256("ze")).build();
        DecodedJWT verify = ze.verify(token);//验证token 生成一个解析后的jwt对象
        Map<String, Claim> claims = verify.getClaims();
        System.out.println(claims.get("user"));
    }


}

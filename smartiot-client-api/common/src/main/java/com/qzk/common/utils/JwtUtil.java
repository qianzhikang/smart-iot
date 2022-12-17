package com.qzk.common.utils;


import io.jsonwebtoken.*;

import java.util.Map;

/**
 * @Description JWT工具
 * @Date 2022-12-17-17-07
 * @Author qianzhikang
 */
public class JwtUtil {

    /**
     * 生成JWT串
     * @param secret 密钥
     * @param claims 载体信息
     * @return JWT串
     */
    public static String generateJwt(String secret, Map<String, Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    /**
     * 解析token
     *
     * @param secret 密钥
     * @param token token
     * @return  map
     */
    public static Map getClaims(String secret, String token) {

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return claims.getBody();
        } catch (MissingClaimException e) {
            e.printStackTrace();
            // we get here if the required claim is not present
        } catch (IncorrectClaimException e) {
            e.printStackTrace();
            // we get here if the required claim has the wrong value
        }
        return null;
    }

}

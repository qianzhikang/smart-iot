package com.qzk.common.utils;


import com.qzk.common.domain.dto.UserTokenDto;
import com.qzk.common.exception.AuthException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

/**
 * @Description JWT工具
 * @Date 2022-12-17-17-07
 * @Author qianzhikang
 */
@Slf4j
public class JwtUtil {

    /**
     * 生成JWT串
     *
     * @param secret 密钥
     * @param claims 载体信息
     * @return JWT串
     */
    public static String generateJwt(String secret, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    /**
     * 解析token
     *
     * @param secret 密钥
     * @param token  token
     * @return map
     */
    public static Map getClaims(String secret, String token) {

        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token);
            return claims.getBody();
        } catch (MissingClaimException e) {
            // we get here if the required claim is not present
            log.error("token解析错误,claim不存在");
            throw new AuthException("非法token");
        } catch (IncorrectClaimException e) {
            // we get here if the required claim has the wrong value
            log.error("token解析错误,格式不正确");
            throw new AuthException("非法token");
        } catch (Exception e) {
            log.error("token解析异常");
            throw new AuthException("非法token");
        }
    }


    /**
     * 获取最后一次登陆时间
     *
     * @param secret 密钥
     * @param token  token
     * @return 最后一次登陆时间
     */
    public static Long getLastLoginDateFromToken(String secret, String token) {
        return UserTokenDto.fromMap(getClaims(secret, token)).getLoginAt();
    }


    /**
     * 获取token的过期时间
     *
     * @param secret 密钥
     * @param token  token
     * @return 过期时间
     */
    public static Long getExpirationDateFromToken(String secret, String token) {
        return UserTokenDto.fromMap(getClaims(secret, token)).getExpires();
    }

    /**
     * 判断token是否过期
     *
     * @param token  token
     * @param secret 密钥
     * @return 已过期返回true，未过期返回false
     */
    public static Boolean isTokenExpired(String secret, String token) {
        Long expirationDateFromToken = getExpirationDateFromToken(secret, token);
        Date expiration = new Date(expirationDateFromToken);
        return expiration.before(new Date());
    }

    /**
     * 计算token的过期时间
     *
     * @param token  token
     * @param secret 密钥
     * @return 过期时间
     */
    public static Date getExpirationTime(String secret, String token) {
        Long lastLoginDateFromToken = getLastLoginDateFromToken(secret, token);
        Long expirationDateFromToken = getExpirationDateFromToken(secret, token);
        return new Date(lastLoginDateFromToken + expirationDateFromToken);
    }


}

package com.qzk.common.redis;

import com.qzk.common.redis.constant.RedisConst;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description Redis工具类
 * @Date 2022-12-17-16-26
 * @Author qianzhikang
 */
@Component
public class TokenSaveRedisDao {

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    /**
     * redis存储前缀
     */
    private final String APP_TOKEN_PREFIX = "smart_iot:app:";

    /**
     * 存储token串
     * @param userId 用户id
     * @param token  token值
     */
    public void saveToken(Integer userId, String token) {
        // 存入Redis
        redisTemplate.opsForValue().set(APP_TOKEN_PREFIX + userId,token);
        // 设置过期时间
        redisTemplate.expire(APP_TOKEN_PREFIX + userId, RedisConst.APP_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
    }


    /**
     * 删除用户token
     * @param userId 用户id
     */
    public void removeToken(Integer userId){
        // 删除token
        redisTemplate.delete(APP_TOKEN_PREFIX + userId);
    }

    /**
     * 获取用户token
     * @param userId 用户id
     * @return token串
     */
    public String getToken(Integer userId){
        return redisTemplate.opsForValue().get(APP_TOKEN_PREFIX + userId);
    }


    /**
     * 检查用户token
     * @param userId 用户id
     * @param token token 值
     * @return 检查结果
     */
    public boolean checkToken(Integer userId, String token) {
        String originToken = redisTemplate.opsForValue().get(APP_TOKEN_PREFIX + userId);
        if (originToken != null && originToken.equals(token)) {
            return true;
        }
        return false;
    }

}

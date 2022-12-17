package com.qzk.user.domain.dto;

import com.qzk.common.redis.constant.RedisConst;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 用户token信息传输类
 * @Date 2022-12-17-17-23
 * @Author qianzhikang
 */
@Data
@NoArgsConstructor
public class UserTokenDto {
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * token 过期时间
     */
    private long expires;

    /**
     * 登陆时间
     */
    private long loginAt;


    public UserTokenDto(Integer userId){
        this.userId = userId;
        this.loginAt = System.currentTimeMillis();
        this.expires = RedisConst.APP_TOKEN_EXPIRE_TIME;
    }

    /**
     * 转换为map结构
     * @return
     */
    public Map toMap(){
        Map map = new HashMap();
        map.put("userId", this.userId);
        map.put("expires", this.expires);
        map.put("loginAt", this.loginAt);
        return map;
    }

    /**
     * 从map结构解析出UserTokenDto类
     * @param map map
     * @return UserTokenDto
     */
    public static UserTokenDto fromMap(Map map){
        UserTokenDto userToken = new UserTokenDto();
        userToken.expires = Long.parseLong(String.valueOf(map.get("expires")));
        userToken.userId = Integer.parseInt(String.valueOf(map.get("userId")));
        userToken.loginAt = Long.parseLong(String.valueOf(map.get("loginAt")));
        return userToken;
    }
}

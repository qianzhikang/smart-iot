package com.qzk.common.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Description MD5加密工具
 * @Date 2022-12-17-15-21
 * @Author qianzhikang
 */
public class MD5Util {

    /**
     * 生成一串16位的盐值
     * @return 盐字符串
     */
    public static String generateSalt(){
        final String originSaltString="zxcvbnmasdfghjklqwertyuiopZXCVBNMASDFGHJKLQWERTYUIOP1234567890,.<>:?";
        Random random = new Random();
        StringBuilder targetStr = new StringBuilder();
        //循环16次，共取出16个随机字符
        for (int i = 0; i < 16; i++) {
            //每次生成一个67以内的随机数
            int number = random.nextInt(originSaltString.length());
            //生成的随机数作为 str 字符串的下标；从 str 中取出随机字符后追加到 stringBuffer
            targetStr.append(originSaltString.charAt(number));
        }
        return targetStr.toString();
    }

    /**
     * 获取加密后的密文
     * @param password 密码
     * @param salt 盐
     * @return 密文串
     */
    public static String getMD5Ciphertext(String password,String salt) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return Base64.encodeBase64String(md5.digest((password + salt).getBytes(StandardCharsets.UTF_8)));
        }catch (NoSuchAlgorithmException e){
            throw new IllegalArgumentException("错误的加密算法类型");
        }
    }


    //public static void main(String[] args) {
        //System.out.println(generateSalt());
    //    System.out.println(getMD5Ciphertext("123456", "GXFkH>nRcm6Bz3BK"));
    //}
}

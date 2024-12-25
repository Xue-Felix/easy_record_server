package com.misu.easy_record_server.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author x
 */
public class EncryptUtil {
    /**
     * MD5加密方法
     *
     * @param password 原始密码
     * @return 加密后的字符串
     */
    public static String encryptPassword(String password) {
        try {
            // 创建MessageDigest实例
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 将密码转换成字节数组
            byte[] bytes = md.digest(password.getBytes());

            // 将字节数组转换成16进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密出错", e);
        }
    }
}

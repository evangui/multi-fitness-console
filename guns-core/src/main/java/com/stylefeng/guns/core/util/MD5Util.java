package com.stylefeng.guns.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密类（封装jdk自带的md5加密方法）
 *
 * @author guiyj007
 * @date 2018年7月2日 下午4:14:22
 */
public class MD5Util {
	/**
	 * 通过盐与明文，获取加密字符串
	 * @param source
	 * @param salt
	 * @return
	 */
	public static String encBySalt(String source, String salt) {
		return encrypt(encrypt(source) + salt);
	}
	
    public static String encrypt(String source) {
        return encodeMd5(source.getBytes());
    }

    private static String encodeMd5(byte[] source) {
        try {
            return encodeHex(MessageDigest.getInstance("MD5").digest(source));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer buffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10)
                buffer.append("0");
            buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
    }
}

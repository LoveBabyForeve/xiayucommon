package com.xiayule.commonlibrary.endecryption;

import android.util.Base64;

/**
 * @Description: 编码工具
 * @Author: 下雨了
 * @CreateDate: 2020-12-16 14:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-16 14:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EncodeUtils {
    private EncodeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 返回base64 编码 字节。
     *
     * @param input 输入
     * @return base64编码字节数
     */
    public static byte[] base64Encode(final String input) {
        return base64Encode(input.getBytes());
    }

    /**
     * 返回base64 编码 字节。
     *
     * @param input 输入
     * @return base64编码字节数
     */
    public static byte[] base64Encode(final byte[] input) {
        if (input == null || input.length == 0) return new byte[0];
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * 返回 解码 的base64编码字节。
     *
     * @param input 输入
     * @return 解码base64编码字符串的字符串
     */
    public static byte[] base64Decode(final String input) {
        if (input == null || input.length() == 0) {
            return new byte[0];
        }
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * 返回 解码 的base64编码字节。
     *
     * @param input 输入
     * @return 解码base64编码字节的字节
     */
    public static byte[] base64Decode(byte[] input) {
        if (input == null || input.length == 0) {
            return new byte[0];
        }
        return Base64.decode(input, Base64.NO_WRAP);
    }


}


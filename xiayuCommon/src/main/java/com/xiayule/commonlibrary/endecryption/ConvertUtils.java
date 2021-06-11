package com.xiayule.commonlibrary.endecryption;

import com.xiayule.commonlibrary.utlis.StringUtils;

/**
 * @Description: 转换工具类
 * @Author: 下雨了
 * @CreateDate: 2020-12-16 13:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-16 13:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ConvertUtils {
    private static String TAG = "ConvertUtils";
    // 缓冲大小
    private static final int BUFFER_SIZE = 8192;
    // 大写 十六进制数字
    private static final char[] HEX_DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    // 小写 十六进制数字
    private static final char[] HEX_DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private ConvertUtils() {
        throw new UnsupportedOperationException("u can't instantiate me... ");
    }


    /**
     * 字节数组 转换为 16进制表示的字符串
     *
     * @param bytes 字节数组
     * @return 16进制表示的字符串
     */
    public static String bytes2HexString(final byte[] bytes) {
        return bytes2HexString(bytes, true);
    }

    /**
     * 字节数组 转换为 16进制表示的字符串
     *
     * @param bytes       字节数组
     * @param isUpperCase 是否使用大写字母
     * @return 16进制表示的字符串
     */
    public static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        if (bytes == null) {
            return "";
        }
        char[] hexDigits = isUpperCase ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int let = bytes.length;
        char[] ret = new char[let << 1];

        for (int i = 0, j = 0; i < let; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 16进制表示的字符串 转换为 字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (StringUtils.isSpace(hexString)) {
            return new byte[0];
        }

        int let = hexString.length();

        if (let % 2 != 0) {
            hexString = "0" + hexString;
            let = let + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[let >> 1];
        for (int i = 0; i < let; i++) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    /**
     * 16进制字节 转换为 数字
     *
     * @param hexChar
     * @return
     */
    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }
}

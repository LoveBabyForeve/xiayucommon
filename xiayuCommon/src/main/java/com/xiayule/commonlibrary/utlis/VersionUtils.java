package com.xiayule.commonlibrary.utlis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: 获取 App版本工具
 * @Author: 下雨了
 * @CreateDate: 2020-11-24 15:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-24 15:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class VersionUtils {
    /**
     * 获取最低系统版本号
     */
    public static String getMinSdkVersion(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return String.valueOf(context.getApplicationInfo().minSdkVersion);
        } else {
            return "";
        }
    }

    /**
     * 获取目前系统版本号
     */
    public static String getTargetSdkVersion(Context context) {
        return String.valueOf(context.getApplicationInfo().targetSdkVersion);
    }

    /**
     * 返回当前程序 版本号 versionCode
     */
    public static String getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
            KLog.e("VersionInfo----Exception", e);
        }
        return versioncode + "";
    }

    /**
     * 返回当前程序 版本名 versionName
     */
    public static String getAppVersionName(Context context) {
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {
            KLog.e("VersionInfo----Exception", e);
        }
        return versionName;
    }

    /**
     * 返回当前程序 包名 applicationId
     */
    public static String getAppPackageName(Context context) {
        String packageName = null;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            packageName = pi.packageName;
        } catch (Exception e) {
            KLog.e("VersionInfo----Exception", e);
        }
        return packageName;
    }

    /**
     * 返回应用程序对 MD5 值的签名。
     * Return the application's signature for MD5 value.
     */
    public static String getAppSignatureMD5(Context context) {
        return getAppSignatureHash(getAppPackageName(context), "MD5");
    }

    /**
     * 返回应用程序对 SHA1 值的签名。
     * Return the application's signature for SHA1 value.
     */
    public static String getAppSignatureSHA1(Context context) {
        return getAppSignatureHash(getAppPackageName(context), "SHA1");
    }

    /**
     * 返回应用程序对 SHA256 值的签名。
     * Return the application's signature for SHA256 value.
     */
    public static String getAppSignatureSHA256(Context context) {
        return getAppSignatureHash(getAppPackageName(context), "SHA256");
    }

    /**
     * 获取 Signature（签名） Hash
     *
     * @param packageName 唯一包名
     * @param algorithm   类型
     * @return ""
     */
    public static String getAppSignatureHash(final String packageName, final String algorithm) {
        if (packageName.isEmpty()) {
            return "";
        }
        Signature[] signature = getAppSignature(packageName);
        if (signature == null || signature.length <= 0) {
            return "";
        }
        return bytes2HexString(hashTemplate(signature[0].toByteArray(), algorithm))
                .replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 获取 App Signature（签名）信息
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (packageName.isEmpty()) {
            return null;
        }
        try {
            PackageManager packageManager = Utils.getContext().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo == null ? null : packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回哈希加密的字节。
     * Return the bytes of hash encryption.
     *
     * @param data      The data.
     * @param algorithm The name of hash encryption.
     * @return the bytes of hash encryption
     */
    public static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字节到十六进制字符串。
     * Bytes to hex string.
     * <p>e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns "00A8"</p>
     *
     * @param bytes The bytes.
     * @return hex string
     */
    public static String bytes2HexString(final byte[] bytes) {
        return bytes2HexString(bytes, true);
    }

    private static final char[] HEX_DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 字节到十六进制字符串。
     * Bytes to hex string.
     * <p>e.g. bytes2HexString(new byte[] { 0, (byte) 0xa8 }, true) returns "00A8"</p>
     *
     * @param bytes       The bytes.
     * @param isUpperCase True to use upper case, false otherwise.
     * @return hex string
     */
    public static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        if (bytes == null) return "";
        char[] hexDigits = isUpperCase ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }
}

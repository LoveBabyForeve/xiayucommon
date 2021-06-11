package com.xiayule.commonlibrary.logcat;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.xiayule.commonlibrary.utlis.NetworkUtils;
import com.xiayule.commonlibrary.utlis.SPUtils;
import com.xiayule.commonlibrary.utlis.SystemUtil;
import com.xiayule.commonlibrary.utlis.VersionUtils;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * @Description: App信息管理
 * @Author: 下雨了
 * @CreateDate: 2020-11-27 15:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-27 15:33
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class AppInfoManager {
    private static final String TAG = "AppInfoManager";

    private static AppInfoManager mAppInfoManager;

    private Context mContext;

    // 实例化
    public static synchronized AppInfoManager getInstance() {
        if (mAppInfoManager == null) {
            mAppInfoManager = new AppInfoManager();
        }
        return mAppInfoManager;
    }

    // 初始化 Context
    public void init(Context context) {
        mContext = context;
    }

    // 获取App全部信息
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getAppInfo() {
        HashMap<String, String> map = new HashMap();
        map.put("包名", getPackName());
        map.put("应用版本名", getVersionName());
        map.put("应用版本号", getVersionCode());
        map.put("最低系统版本号", getMinSdkVersion());
        map.put("目前系统版本号", getTargetSdkVersion());
        map.put("Sign MD5", getSignMD5());
        map.put("Sign SHA1", getSignSHA1());
        map.put("Sign SHA256", getSignSHA256());
        map.put("手机型号", getSystemModel());
        map.put("系统版本", getSystemVersion());
        map.put("SD卡剩余空间", getSDCardSpace());
        map.put("系统剩余空间 -/-", getRomSpace());
        map.put("系统剩余空间", getRomEmptySpace());
        map.put("系统总空间", getRomAllSpace());
        map.put("屏幕分辨率", getWidthPixels());
        map.put("屏幕尺寸", getScreenInch());
        map.put("是否root", isDeviceRooted() ? "是" : "否");
        map.put("密度", getDensity());
        map.put("IP", getIPAddress());
        map.put("获取IMEI", getIMEI());
        map.put("当前网络", getNetType());
        map.put("CPU占有率", getCpuData());
        map.put("占用内存", getMemoryData());

        return new Gson().toJson(map);
    }

    // 获取当前所需要的信息 （定制需求）
    public String getAppInfoCommon() {
        HashMap<String, String> map = new HashMap<>();
        map.put("deviceNetType", getNetType());
        map.put("deviceTypeString", getSystemModel());
        map.put("deviceSystemVersion", getSystemVersion());
        map.put("appVersion", getVersionName());
        map.put("uid", SPUtils.getInstance().contains("userId") ? SPUtils.getInstance().getString("userId") : "");
        map.put("usedCPU", getCpuData());
        map.put("appTakeUpMemory", getMemoryData());
        map.put("deviceDisk", getRomEmptySpace().substring(0, getRomEmptySpace().length() - 2));
        map.put("deviceAllDisk", getRomAllSpace().substring(0, getRomAllSpace().length() - 2));
        return new Gson().toJson(map);
    }

    // 获取包名
    public String getPackName() {
        return VersionUtils.getAppPackageName(mContext);
    }

    // 获取应用版本名
    public String getVersionName() {
        return VersionUtils.getAppVersionName(mContext);
    }

    // 获取应用版本号
    public String getVersionCode() {
        return VersionUtils.getAppVersionCode(mContext);
    }

    // 获取最低系统版本号
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getMinSdkVersion() {
        return VersionUtils.getMinSdkVersion(mContext);
    }

    // 获取目前系统版本号
    public String getTargetSdkVersion() {
        return VersionUtils.getTargetSdkVersion(mContext);
    }

    // 获取App Sign MD5
    public String getSignMD5() {
        return VersionUtils.getAppSignatureMD5(mContext);
    }

    // 获取App Sign SHA1
    public String getSignSHA1() {
        return VersionUtils.getAppSignatureSHA1(mContext);
    }

    // 获取App Sign SHA256
    public String getSignSHA256() {
        return VersionUtils.getAppSignatureSHA256(mContext);
    }

    // 获取手机型号
    public String getSystemModel() {
        return SystemUtil.getSystemModel();
    }

    // 获取系统版本
    public String getSystemVersion() {
        return SystemUtil.getSystemVersion();
    }

    // 获取SD卡剩余空间
    public String getSDCardSpace() {
        return SystemUtil.getSDCardSpace(mContext);
    }

    // 获取系统剩余空间  -/-
    public String getRomSpace() {
        return SystemUtil.getRomSpace(mContext);
    }

    // 获取系统剩余空间
    public String getRomEmptySpace() {
        return SystemUtil.getRomAvailableSize(mContext);
    }

    // 获取系统总空间
    public String getRomAllSpace() {
        return SystemUtil.getRomTotalSize(mContext);
    }

    // 获取屏幕分辨率
    public String getWidthPixels() {
        return SystemUtil.getWidthPixels(mContext) + "x" + SystemUtil.getRealHeightPixels(mContext);
    }

    // 获取屏幕尺寸
    public String getScreenInch() {
        return SystemUtil.getScreenInch((Activity) mContext) + "";
    }

    // 获取是否root
    public boolean isDeviceRooted() {
        return SystemUtil.isDeviceRooted();
    }

    // 获取密度
    public String getDensity() {
        return String.valueOf(SystemUtil.getScreenDensity());
    }

    // 获取IP
    public String getIPAddress() {
        return NetworkUtils.getIpAddress(mContext);
    }

    // 获取Mac
    public String getMacAddress() {
        return "";
    }

    // 获取IMEI
    public String getIMEI() {
        return SystemUtil.getIMEI(mContext);
    }

    // 当前网络
    public String getNetType() {
        switch (NetworkUtils.getNetType()) {
            case NETWORK_ETHERNET: // 以太网
                return "Ethernet";
            case NETWORK_WIFI:
                return "WIFI";
            case NETWORK_5G:
                return "5G";
            case NETWORK_4G:
                return "4G";
            case NETWORK_3G:
                return "3G";
            case NETWORK_2G:
                return "2G";
            case NETWORK_UNKNOWN: // 未知的
                return "unknown";
            case NETWORK_NO:
                return "no";
            default:
                return "";
        }
    }

    // CPU占有率
    public String getCpuData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.valueOf(SystemUtil.getCpuDataForO());
        } else {
            return String.valueOf(SystemUtil.getCpuData());
        }
    }

    // 获取内存占用
    public String getMemoryData() {
//        return String.valueOf(SystemUtil.getMemoryData());
//        return String.format("%.2f", SystemUtil.getMemoryData());
        return new DecimalFormat("#.00").format(SystemUtil.getMemoryData());
    }

}

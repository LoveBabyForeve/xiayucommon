package com.xiayule.commonlibrary.logcat;

import android.content.Intent;
import android.os.Bundle;

import com.xiayule.commonlibrary.utlis.Utils;

import java.io.File;
import java.io.Serializable;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2021-01-12 11:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-01-12 11:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SavaLogcatManager {
    private static final String TAG = "SavaLogcatManager";

    // 日志保存的条数
    public static final String LOGCAT_NUM = "Logcat_Num";

    // 日志保存的时间
    public static final String LOGCAT_TIME = "Logcat_Time";


    public static final String BASE = "Log";
    // 缓存 文件夹
    public static final String CRASH = "Crash";
    // 卡顿 文件夹
    public static final String BLOCK_MONITOR = "ANR";
    // 首屏加载时长 文件夹
    public static final String LOADING_TIME = "H5LoadTiming";
    // 网络错误日志 文件夹
    public static final String NETWORK_ERROR = "NetError";
    // activity跳转路径 文件夹
    public static final String ACTIVITY_PATH = "Activity";
    // 文件上传日志 文件夹
    public static final String FILE_UPLOAD = "FileUpload";
    // button按钮点击 文件夹
    public static final String BUTTON_PATH = "ButtonActivity";


    // 缓存 文件名
    public static final String CRASH_NAME = "androidcrash";
    // 卡顿 文件名
    public static final String BLOCK_MONITOR_NAME = "androidanr";
    // 首屏加载时长 文件名
    public static final String LOADING_TIME_NAME = "androidh5loadtiming";
    // 网络错误日志 文件名
    public static final String NETWORK_ERROR_NAME = "androidneterror";
    // activity跳转路径 文件名
    public static final String ACTIVITY_PATH_NAME = "androidactivity";
    // 文件上传日志 文件名
    public static final String FILE_UPLOAD_NAME = "androidfileipload";
    // button按钮点击 文件名
    public static final String BUTTON_PATH_NAME = "androidbuttonactivity";


    // 保存文件的类型
    public static final String TYPE_JSON = "json";

    public static final String TYPE_ZIP = "zip";


    private static SavaLogcatManager mSavaLogcatManager;

    public static synchronized SavaLogcatManager getInstance() {
        if (mSavaLogcatManager == null) {
            mSavaLogcatManager = new SavaLogcatManager();
        }
        return mSavaLogcatManager;
    }

    /**
     * 保存日志
     *
     * @param ser  内容
     * @param file 文件保存路径
     */
    public void saveLogcat(Serializable ser, File file) {
        saveLogcat(ser, file, false);
    }

    /**
     * 保存日志
     *
     * @param ser      内容
     * @param filePath 文件保存路径
     * @param isAdd    是否追加  {@code true}:追加 <br> {@code false}:不追加
     */
    public void saveLogcat(Serializable ser, String filePath, boolean isAdd) {
        saveLogcat(ser, new File(filePath), isAdd);
    }

    /**
     * 保存日志
     *
     * @param ser   内容
     * @param file  文件保存路径
     * @param isAdd 是否追加  {@code true}:追加 <br> {@code false}:不追加
     */
    public void saveLogcat(Serializable ser, File file, boolean isAdd) {


        Intent intent = new Intent(Utils.getContext(), SaveLogcatServer.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ser", ser);
        bundle.putString("file", file.getAbsolutePath());
        bundle.putBoolean("isAdd", isAdd);
        intent.putExtras(bundle);

        Utils.getContext().startService(intent);

    }
}

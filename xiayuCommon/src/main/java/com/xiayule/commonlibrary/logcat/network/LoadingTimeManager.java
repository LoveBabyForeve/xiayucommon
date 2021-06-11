package com.xiayule.commonlibrary.logcat.network;

import com.xiayule.commonlibrary.logcat.AppInfoManager;
import com.xiayule.commonlibrary.logcat.SavaLogcatManager;
import com.xiayule.commonlibrary.logcat.utils.FileUtils;
import com.xiayule.commonlibrary.logcat.utils.JsonMergeUtils;
import com.xiayule.commonlibrary.utlis.NetworkUtils;

import java.io.Serializable;

/**
 * @Description: 首屏加载时间管理
 * @Author: 下雨了
 * @CreateDate: 2020-12-08 17:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-08 17:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LoadingTimeManager {
    public static final String TAG = "LoadingTimeManager";

    private static LoadingTimeManager mLoadingTimeManager;

    // 实例唯一化
    public static synchronized LoadingTimeManager getInstance() {
        if (mLoadingTimeManager == null) {
            mLoadingTimeManager = new LoadingTimeManager();
        }
        return mLoadingTimeManager;
    }

    // 判断是否正常运行
    private boolean mIsRunning;

    /**
     * 开启日志监控，进行保存日志
     */
    public void start() {
        if (mIsRunning) {
            return;
        }
        mIsRunning = true;
    }

    /**
     * 关闭日志监控，关闭日志保存
     */
    public void stop() {
        if (!mIsRunning) {
            return;
        }
        mIsRunning = false;
    }

    /**
     * 保存 首页加载时长到文件
     *
     * @param s
     */
    public void saveLoadingTime(String s) {
        if (mIsRunning) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String str = NetworkUtils.getLoadingTime(s);
                    // 添加用户信息
                    String appInfos = AppInfoManager.getInstance().getAppInfoCommon();
                    // 拼接json串
                    String object = JsonMergeUtils.mergeJsonObject(appInfos, str);
                    SavaLogcatManager.getInstance().saveLogcat((Serializable) object, FileUtils.getLogcatCacheFile(SavaLogcatManager.LOADING_TIME, SavaLogcatManager.LOADING_TIME_NAME, SavaLogcatManager.TYPE_JSON));

                }
            }).start();

        }
    }
}

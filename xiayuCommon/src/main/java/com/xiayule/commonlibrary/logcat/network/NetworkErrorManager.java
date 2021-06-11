package com.xiayule.commonlibrary.logcat.network;

import com.google.gson.Gson;
import com.xiayule.commonlibrary.logcat.AppInfoManager;
import com.xiayule.commonlibrary.logcat.SavaLogcatManager;
import com.xiayule.commonlibrary.logcat.utils.FileUtils;
import com.xiayule.commonlibrary.logcat.utils.JsonMergeUtils;
import com.xiayule.commonlibrary.utlis.DateUtils;
import com.xiayule.commonlibrary.utlis.SPUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Description: 网络错误日志管理
 * @Author: 下雨了
 * @CreateDate: 2020-12-16 15:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-16 15:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NetworkErrorManager {
    public static final String TAG = "NetworkErrorManager";

    private static NetworkErrorManager mNetworkErrorManager;

    // 实例唯一化
    public static synchronized NetworkErrorManager getInstance() {
        if (mNetworkErrorManager == null) {
            mNetworkErrorManager = new NetworkErrorManager();
        }
        return mNetworkErrorManager;
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
     * 保存 网络错误日志到文件
     *
     * @param str
     */
    public void saveNetworkError(String str) {
        if (mIsRunning) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String fileName;
                    if (!SPUtils.getInstance().contains(TAG)) {
                        fileName = FileUtils.getLogcatCacheFile(SavaLogcatManager.NETWORK_ERROR, SavaLogcatManager.NETWORK_ERROR_NAME, SavaLogcatManager.TYPE_JSON).getAbsolutePath();
                        SPUtils.getInstance().put(TAG, fileName);
                    } else {
                        fileName = SPUtils.getInstance().getString(TAG);
                        if (!FileUtils.fileIsExists(fileName)) {
                            fileName = FileUtils.getLogcatCacheFile(SavaLogcatManager.NETWORK_ERROR, SavaLogcatManager.NETWORK_ERROR_NAME, SavaLogcatManager.TYPE_JSON).getAbsolutePath();
                            SPUtils.getInstance().put(TAG, fileName);
                        }
                    }
                    HashMap<String, String> map = new HashMap<>();
                    map.put("_id", DateUtils.getCurrent() + "_" + (int) ((Math.random() * 9 + 1) * 100000));
                    HashMap<String, HashMap> hashMap = new HashMap<>();
                    hashMap.put("index", map);
                    String index = new Gson().toJson(hashMap);

                    // 添加用户信息
                    String appInfos = AppInfoManager.getInstance().getAppInfoCommon();
                    // 拼接json串
                    String object = JsonMergeUtils.mergeJsonObject(appInfos, str);

                    SavaLogcatManager.getInstance().saveLogcat((Serializable) index + "\r\n" + object, fileName, true);
                }
            }).start();
        }
    }
}

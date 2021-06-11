package com.xiayule.commonlibrary.logcat.defined;

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
 * @Description: 文件上传管理
 * @Author: 下雨了
 * @CreateDate: 2020-12-29 16:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-29 16:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FileUploadManager {
    public static final String TAG = "FileUploadManager";

    private static FileUploadManager mFileUploadManager;

    // 实例唯一化
    public static synchronized FileUploadManager getInstance() {
        if (mFileUploadManager == null) {
            mFileUploadManager = new FileUploadManager();
        }
        return mFileUploadManager;
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

    public void saveFileUpload(String str) {
        if (mIsRunning) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String fileName;
                    if (!SPUtils.getInstance().contains(TAG)) {
                        fileName = FileUtils.getLogcatCacheFile(SavaLogcatManager.FILE_UPLOAD, SavaLogcatManager.FILE_UPLOAD_NAME, SavaLogcatManager.TYPE_JSON).getAbsolutePath();
                        SPUtils.getInstance().put(TAG, fileName);
                    } else {
                        fileName = SPUtils.getInstance().getString(TAG);
                        if (!FileUtils.fileIsExists(fileName)) {
                            fileName = FileUtils.getLogcatCacheFile(SavaLogcatManager.ACTIVITY_PATH, SavaLogcatManager.ACTIVITY_PATH_NAME, SavaLogcatManager.TYPE_JSON).getAbsolutePath();
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

package com.xiayule.commonlibrary.logcat.blockmonitor;

import android.content.Context;
import android.os.Looper;

import com.xiayule.commonlibrary.logcat.AppInfoManager;
import com.xiayule.commonlibrary.logcat.SavaLogcatManager;
import com.xiayule.commonlibrary.logcat.blockmonitor.bean.BlockInfo;
import com.xiayule.commonlibrary.logcat.utils.FileUtils;
import com.xiayule.commonlibrary.logcat.utils.JsonMergeUtils;
import com.xiayule.commonlibrary.utlis.KLog;

import java.io.Serializable;

/**
 * @Description: 卡顿管理
 * @Author: 下雨了
 * @CreateDate: 2020-12-01 15:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-01 15:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BlockMonitorManager {
    private static final String TAG = "BlockMonitorManager";

    private static BlockMonitorManager mBlockMonitorManager;

    private Context mContext;
    // 判断是否正常运行
    private boolean mIsRunning;

    // 检测卡顿日志
    private MonitorCore mMonitorCore;

    // 实例唯一化
    public static synchronized BlockMonitorManager getInstance() {
        if (mBlockMonitorManager == null) {
            mBlockMonitorManager = new BlockMonitorManager();
        }
        return mBlockMonitorManager;
    }

    // 初始化 Context
    public void init(Context context) {
        mContext = context;
    }

    // 开启
    public void start() {
        if (mIsRunning) {
            KLog.i(TAG, "卡顿检测正在运行");
            return;
        }

        if (mMonitorCore == null) {
            mMonitorCore = new MonitorCore();
        }
        mIsRunning = true;
        // 在主线程开启一个Looper，打印出接受到的日志信息
        Looper.getMainLooper().setMessageLogging(mMonitorCore);

    }

    // 停止
    public void stop() {
        if (!mIsRunning) {
            KLog.i(TAG, "卡顿检测 已经关闭");
            return;
        }
        // 关闭卡顿日志打印
        Looper.getMainLooper().setMessageLogging(null);
        mIsRunning = false;
    }

    // 清除 崩溃日志
    public void clearCacheHistory() {
        FileUtils.deleteDirectory(FileUtils.getLogcatCacheDir(SavaLogcatManager.BLOCK_MONITOR));
    }


    /**
     * 通知卡顿事件  未完成
     */
    @Deprecated
    void notifyBlockEvent(BlockInfo blockInfo) {
        blockInfo.concernStackString = "";

//        KLog.i(TAG, blockInfo.toString());

    }

    /**
     * 保存日志
     */
    void saveObje(BlockInfo blockInfo) {
        // 添加用户信息
        String appInfos = AppInfoManager.getInstance().getAppInfoCommon();
        // 拼接json串
        String object = JsonMergeUtils.mergeJsonObject(appInfos, blockInfo.toString());
        SavaLogcatManager.getInstance().saveLogcat((Serializable) object, FileUtils.getLogcatCacheFile(SavaLogcatManager.BLOCK_MONITOR, SavaLogcatManager.BLOCK_MONITOR_NAME, SavaLogcatManager.TYPE_JSON));

    }
}

package com.xiayule.commonlibrary.logcat.crash;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.xiayule.commonlibrary.logcat.AppInfoManager;
import com.xiayule.commonlibrary.logcat.SavaLogcatManager;
import com.xiayule.commonlibrary.logcat.utils.FileUtils;
import com.xiayule.commonlibrary.logcat.utils.JsonMergeUtils;
import com.xiayule.commonlibrary.utlis.ToastUtils;

import java.io.Serializable;
import java.util.HashMap;


/**
 * @Description: 系统崩溃异常捕获（参照 DoKit）
 * @Author: 下雨了
 * @CreateDate: 2020-11-26 14:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-26 14:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CrashCaptureManager implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashCaptureManager";

    private static CrashCaptureManager mCrashCaptureManager;

    private final Thread.UncaughtExceptionHandler mDefaultHandler;
    private final Handler mHandler;
    private Context mContext;


    // 构造方法
    private CrashCaptureManager() {
        // 获取默认的系统异常捕获器
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 创建HandlerThread实例对象 并标记该线程
        HandlerThread handlerThread = new HandlerThread(TAG);
        // 启动线程
        handlerThread.start();
        // 消息处理操作
        this.mHandler = new Handler(handlerThread.getLooper());
    }

    // 实例唯一化
    public static synchronized CrashCaptureManager getInstance() {
        if (mCrashCaptureManager == null) {
            mCrashCaptureManager = new CrashCaptureManager();
        }
        return mCrashCaptureManager;
    }

    // 初始化 Context
    public void init(Context context) {
        mContext = context;
    }

    // 开启
    public void start() {
        // 把当前的crash捕获器设置成默认的crash捕获
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    // 停止
    public void stop() {
        // 恢复系统异常捕获器
        Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
    }

    // 清除 崩溃日志
    public void clearCacheHistory() {
        FileUtils.deleteDirectory(FileUtils.getLogcatCacheDir(SavaLogcatManager.CRASH));
    }

    private void post(Runnable r) {
        mHandler.post(r);
    }

    private void postDelay(Runnable r, long delayMillis) {
        mHandler.postDelayed(r, delayMillis);
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Thread", t.getName());
        map.put("name", e.getClass().getName());
        map.put("stackArray", Log.getStackTraceString(e));
        map.put("reason", e.getMessage());
        String str = new Gson().toJson(map);
        // 保存崩溃信息
        // 添加用户信息
        String appInfos = AppInfoManager.getInstance().getAppInfoCommon();
        // 拼接json串
        String object = JsonMergeUtils.mergeJsonObject(appInfos, str);
        SavaLogcatManager.getInstance().saveLogcat((Serializable) object, FileUtils.getLogcatCacheFile(SavaLogcatManager.CRASH, SavaLogcatManager.CRASH_NAME, SavaLogcatManager.TYPE_JSON));
        //保存埋点数据

        post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showLong("很抱歉,程序出现异常！");
            }
        });
        postDelay(new Runnable() {
            @Override
            public void run() {
                if (mDefaultHandler != null) {
                    // 如果用户没有处理则让系统默认的异常处理器来处理
                    mDefaultHandler.uncaughtException(t, e);
                }
            }
        }, 2000);

    }
}

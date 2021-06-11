package com.xiayule.commonlibrary.logcat.blockmonitor;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;


import com.xiayule.commonlibrary.utlis.DateUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description: 堆栈信息采集类
 * @Author: 下雨了
 * @CreateDate: 2020-12-01 15:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-01 15:51
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StackSampler {
    private static final String TAG = "StackSampler";
    // 默认采集间隔
    private static final int DEFAULT_SAMPLE_INTERVAL = 300;
    // 默认输入最大数
    private static final int DEFAULT_MAX_ENTRY_COUNT = 100;


    // 异步Handler线程
    private HandlerThread mStackThread;
    private Handler mStackHandler;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            dumpInfo();
            if (mAtomicBoolean.get()) {
                mStackHandler.postDelayed(mRunnable, DEFAULT_SAMPLE_INTERVAL);
            }
        }
    };

    // 原子布尔 可用在应用程序中（如以原子方式更新的标志），但不能用于替换 Boolean。
    // 换一句话说，Atomic就是原子性的意思，即能够保证在高并发的情况下只有一个线程能够访问这个属性值。
    // 我们使用 AtomicBoolean 高效并发处理 “只初始化一次” 的功能要求
    private AtomicBoolean mAtomicBoolean = new AtomicBoolean(false);


    private String mFilterCache;

    // 构造方法
    public StackSampler() {
    }

    // 初始化
    public void init() {
        if (mStackThread == null) {
            // 实例对象，参数为线程名字
            mStackThread = new HandlerThread("BlockMonitor") {
                @Override
                protected void onLooperPrepared() {
                    // 在Looper开启之前 重设 Handler
                    mStackHandler = new Handler(mStackThread.getLooper());
                }
            };
            // 启动线程
            mStackThread.start();
        }
    }

    // 开始转储
    public void startDump() {
        if (mStackHandler == null) {
            return;
        }
        if (mAtomicBoolean.get()) {
            return;
        }
        mAtomicBoolean.set(true);
        // 移除回调
        mStackHandler.removeCallbacks(mRunnable);
        // 延迟300
        mStackHandler.postDelayed(mRunnable, DEFAULT_MAX_ENTRY_COUNT);
    }

    // 停止转储
    public void stopDump() {
        if (mStackHandler == null) {
            return;
        }
        if (!mAtomicBoolean.get()) {
            return;
        }
        mAtomicBoolean.set(false);
        mFilterCache = null;
        mStackHandler.removeCallbacks(mRunnable);

    }

    // 关闭
    public void shutDown() {
        stopDump();
        if (mStackThread != null) {
            // 关闭线程
            mStackThread.quit();
        }
    }


    //=================================================================================================//
    private static final String SEPARATOR = "\r\n";
    private LinkedHashMap<Long, String> sStackMap = new LinkedHashMap<>();

    // 转储信息
    private void dumpInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        Thread thread = Looper.getMainLooper().getThread();
        for (StackTraceElement stackTraceElement : thread.getStackTrace()) {
            stringBuilder.append(stackTraceElement.toString()).append(SEPARATOR);
        }
        // 同步
        synchronized (sStackMap) {
            if (sStackMap.size() == DEFAULT_MAX_ENTRY_COUNT) {
                // 如果超过100个，就移除第一个
                sStackMap.remove(sStackMap.keySet().iterator().next());
            }
            if (!shouldIgnore(stringBuilder)) {
                sStackMap.put(System.currentTimeMillis(), stringBuilder.toString());
            }
        }

    }

    /**
     * 过滤掉重复项
     */
    private boolean shouldIgnore(StringBuilder builder) {
        if (TextUtils.equals(mFilterCache, builder.toString())) {
            return true;
        }
        mFilterCache = builder.toString();
        return false;
    }

    /**
     * 获取 线程堆栈 信息条目
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 时间段内的信息条目
     */
    public ArrayList<String> getThreadStackEntries(long startTime, long endTime) {
        ArrayList<String> result = new ArrayList<>();
        synchronized (sStackMap) {
            for (long entryTime : sStackMap.keySet()) {
                if (startTime < entryTime && entryTime < endTime) {
                    result.add(DateUtils.getTime(entryTime, "MM-dd HH:mm:ss.SSS") + SEPARATOR + SEPARATOR + sStackMap.get(entryTime));
                }
            }
        }
        return result;
    }
}

package com.xiayule.commonlibrary.logcat.blockmonitor;

import android.os.SystemClock;
import android.util.Printer;

import com.xiayule.commonlibrary.logcat.blockmonitor.bean.BlockInfo;

import java.util.ArrayList;

/**
 * @Description: 检测卡顿日志类
 * @Author: 下雨了
 * @CreateDate: 2020-12-01 15:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-01 15:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MonitorCore implements Printer {
    private static final String TAG = "MonitorCore";

    /**
     * 卡顿阈值
     */
    private static final int BLOCK_THRESHOLD_MILLIS = 200;

    // 开始时间
    private long mStartTime = 0;
    // 线程开始时间
    private long mStartThreadTime = 0;
    // 打印是否启动
    private boolean mPrintingStarted = false;


    // 堆栈信息采集
    private StackSampler mStackSampler;

    // 初始化 构造方法
    public MonitorCore() {
        mStackSampler = new StackSampler();
        mStackSampler.init();
    }

    @Override
    public void println(String x) {
        if (!mPrintingStarted) {
            // 当前时间
            mStartTime = System.currentTimeMillis();
            // 返回在当前线程中运行的毫秒
            mStartThreadTime = SystemClock.currentThreadTimeMillis();
            mPrintingStarted = true;
            mStackSampler.startDump();
        } else {
            final long endTime = System.currentTimeMillis();
            final long endThreadTime = SystemClock.currentThreadTimeMillis();
            mPrintingStarted = false;
            if (isBlock(endTime)) {
                // 获取 线程堆栈 信息条目
                final ArrayList<String> entries = mStackSampler.getThreadStackEntries(mStartTime, endTime);
                if (entries.size() >= 0) {
                    final BlockInfo blockInfo = BlockInfo.newInstance()
                            .setMainThreadTimeCost(mStartTime, endTime, mStartThreadTime, endThreadTime)
                            .setThreadStackEntries(entries)
                            .flushString();
                    //TODO 此处通知卡顿事件，可换成写入本地日志
                    BlockMonitorManager.getInstance().notifyBlockEvent(blockInfo);
                    BlockMonitorManager.getInstance().saveObje(blockInfo);
                }
            }

            mStackSampler.stopDump();
        }
    }

    private boolean isBlock(long endTime) {
        return endTime - mStartTime > BLOCK_THRESHOLD_MILLIS;
    }

}

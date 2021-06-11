package com.xiayule.commonlibrary.logcat.blockmonitor.bean;

import com.google.gson.Gson;
import com.xiayule.commonlibrary.utlis.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @Description: 卡顿信息类
 * @Author: 下雨了
 * @CreateDate: 2020-12-01 17:35
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-01 17:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BlockInfo {

    // 消耗时间
    public long timeCost;
    // 线程消耗时间
    private long threadTimeCost;

    // 开始时间
    public String timeStart;
    // 结束时间
    private String timeEnd;
    //  线程堆栈 卡顿信息
    public ArrayList<String> threadStackEntries = new ArrayList<>();


    // 实例化
    public static BlockInfo newInstance() {
        BlockInfo blockInfo = new BlockInfo();
        return blockInfo;
    }

    /**
     * 设置 主线程时间 耗时
     *
     * @param realTimeStart   开始时间
     * @param realTimeEnd     结束时间
     * @param threadTimeStart 线程开始时间
     * @param threadTimeEnd   线程结束时间
     * @return
     */
    public BlockInfo setMainThreadTimeCost(long realTimeStart, long realTimeEnd, long threadTimeStart, long threadTimeEnd) {
        timeCost = realTimeEnd - realTimeStart;
        threadTimeCost = threadTimeEnd - threadTimeStart;
        timeStart = DateUtils.getTime(realTimeStart, "MM-dd HH:mm:ss.SSS");
        timeEnd = DateUtils.getTime(realTimeEnd, "MM-dd HH:mm:ss.SSS");

        return this;
    }

    /**
     * 设置 线程栈 条目信息
     *
     * @param threadStackEntries
     * @return
     */
    public BlockInfo setThreadStackEntries(ArrayList<String> threadStackEntries) {
        this.threadStackEntries = threadStackEntries;
        return this;
    }


    //========================================================================================//
    // 换行
    private static final String SEPARATOR = "\r\n";
    private static final String KV = " = ";

    // 当前时间
    private static final String KEY_TIME_COST = "time";
    // 线程时间
    private static final String KEY_THREAD_TIME_COST = "thread-time";
    // 开始时间
    private static final String KEY_TIME_COST_START = "time-start";
    // 结束时间
    private static final String KEY_TIME_COST_END = "time-end";
    // 卡顿信息
    private static final String KEY_STACK = "stack";
    //========================================================================================//

    // 时间信息
    private StringBuilder timeSb = new StringBuilder();
    // 卡顿信息
    private StringBuilder stackSb = new StringBuilder();
    // 存储信息
    private HashMap<String, String> map = new HashMap<>();
    public String concernStackString;

    /**
     * 刷新信息
     */
    public BlockInfo flushString() {

        map.put(KEY_TIME_COST, String.valueOf(timeCost));
        map.put(KEY_THREAD_TIME_COST, String.valueOf(threadTimeCost));
        map.put(KEY_TIME_COST_START, String.valueOf(timeStart));
        map.put(KEY_TIME_COST_END, String.valueOf(timeEnd));


//        timeSb.append(KEY_TIME_COST).append(KV).append(timeCost).append(SEPARATOR);
//        timeSb.append(KEY_THREAD_TIME_COST).append(KV).append(threadTimeCost).append(SEPARATOR);
//        timeSb.append(KEY_TIME_COST_START).append(KV).append(timeStart).append(SEPARATOR);
//        timeSb.append(KEY_TIME_COST_END).append(KV).append(timeEnd).append(SEPARATOR);

        if (threadStackEntries != null && !threadStackEntries.isEmpty()) {
            StringBuilder temp = new StringBuilder();
            for (String s : threadStackEntries) {
                temp.append(s);
                temp.append(SEPARATOR);
            }
//            stackSb.append(KEY_STACK).append(KV).append(temp).append(SEPARATOR);
            map.put(KEY_STACK, String.valueOf(temp));
        }
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(map);
    }

}

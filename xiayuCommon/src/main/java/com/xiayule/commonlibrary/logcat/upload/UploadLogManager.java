package com.xiayule.commonlibrary.logcat.upload;

import android.content.Context;

import com.xiayule.commonlibrary.logcat.AppInfoManager;
import com.xiayule.commonlibrary.logcat.SavaLogcatManager;
import com.xiayule.commonlibrary.logcat.blockmonitor.BlockMonitorManager;
import com.xiayule.commonlibrary.logcat.crash.CrashCaptureManager;
import com.xiayule.commonlibrary.logcat.defined.FileUploadManager;
import com.xiayule.commonlibrary.logcat.network.LoadingTimeManager;
import com.xiayule.commonlibrary.logcat.network.NetworkErrorManager;
import com.xiayule.commonlibrary.logcat.route.ActivityRouteManager;
import com.xiayule.commonlibrary.logcat.route.ButtonRouteManager;
import com.xiayule.commonlibrary.logcat.utils.FileUtils;
import com.xiayule.commonlibrary.utlis.DateUtils;
import com.xiayule.commonlibrary.utlis.NetworkUtils;
import com.xiayule.commonlibrary.utlis.SPUtils;

import java.io.File;


/**
 * @Description: 日志文件上传管理
 * @Author: 下雨了
 * @CreateDate: 2020-12-18 16:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-18 16:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UploadLogManager {
    private static final String TAG = "UploadLogManager";

    private Context mContext;

    private static UploadLogManager mUploadLogManager;


    // 构造函数
    public UploadLogManager(Context context) {
        mContext = context;
    }

    public UploadLogManager() {

    }

    // 实例化
    public static synchronized UploadLogManager getInstance() {
        if (mUploadLogManager == null) {
            mUploadLogManager = new UploadLogManager();
        }
        return mUploadLogManager;
    }

    // 回调接口用于开启网络请求
    private ILogUpload mILogUpload;

    public void setILogUpload(ILogUpload ILogUpload) {
        mILogUpload = ILogUpload;
    }

    /**
     * 设置日志保存的路径
     */
    private File mROOT;

    /**
     * 设置日志压缩的路径
     */
    private File mZipROOT;

    /**
     * 设置缓存文件夹的大小,默认是50MB
     */
    private long mCacheSize = 50 * 1024 * 1024;

    /**
     * 设置在哪种网络状态下上传，true为只在wifi模式下上传，false是wifi和移动网络都上传
     */
    private boolean mWifiOnly = true;

    /**
     * 设置是否立马上传文件 true为是，false为不
     */
    private boolean mUploadOnly = false;

    /**
     * 设置监控日志条数，最大为多少条
     */
    private int mNum = 50;

    /**
     * 日志保存最大天数
     */
    private int mDay = 30;

    /**
     * 日志上传间隔/小时
     */
    private int mHour = 3;

    public File getROOT() {
        return mROOT;
    }

    public File getZipROOT() {
        return mZipROOT;
    }

    public long getCacheSize() {
        return mCacheSize;
    }

    /**
     * 设置缓存文件夹的大小,默认是50MB
     */
    public UploadLogManager setCacheSize(long cacheSize) {
        this.mCacheSize = cacheSize;
        return this;
    }

    /**
     * 设置在哪种网络状态下上传，true为只在wifi模式下上传，false是wifi和移动网络都上传
     */
    public UploadLogManager setWifiOnly(boolean wifiOnly) {
        this.mWifiOnly = wifiOnly;
        return this;
    }

    /**
     * 设置是否立马上传文件 true为是，false为不
     */
    public UploadLogManager setUploadOnly(boolean uploadOnly) {
        mUploadOnly = uploadOnly;
        return this;
    }

    /**
     * 设置监控日志条数，最大为多少条
     */
    public UploadLogManager setNum(int num) {
        this.mNum = num;
        return this;
    }

    /**
     * 设置日志保存最大天数
     */
    public UploadLogManager setDay(int day) {
        this.mDay = day;
        return this;
    }

    /**
     * 设置日志上传间隔
     */
    public UploadLogManager setHour(int hour) {
        this.mHour = hour;
        return this;
    }

    //============================================================ 分割线 ======================================================

    /**
     * 初始化
     */
    public void init(Context mContext) {
        this.mContext = mContext;
        // 设置日志保存的路径
        mROOT = new File(FileUtils.getLogcatCacheDir() + File.separator + "Log");
        //  设置日志压缩的路径
        mZipROOT = FileUtils.getLogcatCacheFile();

        // 初始化 App信息管理
        AppInfoManager.getInstance().init(mContext);

        // 开启 崩溃检测 日志收集
        CrashCaptureManager.getInstance().start();
        // 卡顿检测开启
        BlockMonitorManager.getInstance().start();
        // 原生Activity路由监控
        ActivityRouteManager.getInstance().start();
        // Button 按钮 点击监控
        ButtonRouteManager.getInstance().start();
        // 文件上传管理 监控
        FileUploadManager.getInstance().start();
        // 网络错误日志管理
        NetworkErrorManager.getInstance().start();
        // 首屏加载时间管理
        LoadingTimeManager.getInstance().start();

        // 开启全日志检测
//        LogcatFileManager.getInstance().startLogcatManager(mContext);

        // 删除长时间未上传的文件
        if (SPUtils.getInstance().contains(SavaLogcatManager.LOGCAT_TIME)) {
            // 判断是否大于30天，未上传日志
            if (DateUtils.timeSub(DateUtils.getCurrent(), SPUtils.getInstance().getString(SavaLogcatManager.LOGCAT_TIME)) / 86400 > mDay) {
                // 删除所有 超过30天的日志
                FileUtils.deleteDirectory(mROOT);
                // 清空所有SP保存信息
                clearSpInfo();
            }
        }
    }

    /**
     * 上传文件
     */
    public void upLoadLogFile() {
        // 是否立即上传，是的话直接上传，否的话进行后续判断
        if (!mUploadOnly) {
            //如果网络可用，而且是移动网络，但是用户设置了只在wifi下上传，返回
            if (NetworkUtils.isNetworkAvailable() && !NetworkUtils.isWifi() && mWifiOnly) {
                return;
            }

            // 判断上次上传日志的时候是否超过3小时
            if (SPUtils.getInstance().contains(SavaLogcatManager.LOGCAT_TIME)) {
                if (DateUtils.timeSub(DateUtils.getCurrent(), SPUtils.getInstance().getString(SavaLogcatManager.LOGCAT_TIME)) / 3600 < mHour) {
                    return;
                }
            }

            // 如果设置了条数小于50，返回
            if (SPUtils.getInstance().contains(SavaLogcatManager.LOGCAT_NUM)) {
                if (SPUtils.getInstance().getInt(SavaLogcatManager.LOGCAT_NUM) < mNum) {
                    return;
                }
            } else {
                SPUtils.getInstance().put(SavaLogcatManager.LOGCAT_NUM, 0);
                return;
            }

            // 判断文件夹大小 是否超过设定的值 true是超过
            if (!checkCacheSizeAndDelOldestFile(mROOT)) {
                return;
            }
        }
        mILogUpload.sendFile();


    }

    /**
     * 判断文件夹大小 是否超过设定的值
     *
     * @param dir 文件（夹）地址
     * @return {@code true}:超过 <br> {@code false}:没有超过
     */
    public synchronized boolean checkCacheSizeAndDelOldestFile(File dir) {
        long dirSize = FileUtils.folderSize(dir);
        return dirSize >= getCacheSize();
    }

    /**
     * 清空 追加日志的 保存路径 SP信息
     */
    public UploadLogManager clearSpInfo() {

        // 清除 页面路径 文件名称
        SPUtils.getInstance().remove(ActivityRouteManager.TAG);
        // 清除 文件上传 文件名称
        SPUtils.getInstance().remove(FileUploadManager.TAG);
        // 清除 H5自定义按钮 文件名称
        SPUtils.getInstance().remove(ButtonRouteManager.TAG);
        // 清除 日志条数
        SPUtils.getInstance().remove(SavaLogcatManager.LOGCAT_NUM);
        return this;
    }

    /**
     * 记录上传日志的时间
     */
    public UploadLogManager savaUpTime() {
        // 保存上传时间
        SPUtils.getInstance().put(SavaLogcatManager.LOGCAT_TIME, DateUtils.getCurrent());
        return this;
    }

}

package com.xiayule.commonlibrary.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.kongzue.dialog.util.DialogSettings;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport.UserStrategy;
import com.xiayule.commonlibrary.BuildConfig;
import com.xiayule.commonlibrary.http.networkLoader.HttpRequestProxy;
import com.xiayule.commonlibrary.http.networkLoader.NetRequestType;
import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy;
import com.xiayule.commonlibrary.utlis.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by goldze on 2017/6/15.
 */

public class BaseApplication extends Application {
    private static Application sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        setApplication(this);
    }

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    public static synchronized void setApplication(@NonNull Application application) {
        sInstance = application;
        // 初始化工具类
        Utils.init(application);
        // 初始化图片加载框架
        ImageLoaderProxy.getInstance().init(application);
        // 初始化网络加载框架
        HttpRequestProxy.getInstance().init(NetRequestType.OKHTTP);
        // 初始化 DiaLogV3
        initDialogV3();

        // 初始化 内存泄漏 腾讯bugly
        initBugly();

        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                AppManager.getAppManager().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                AppManager.getAppManager().removeActivity(activity);
            }
        });
    }


    /**
     * 获得当前app运行的Application
     */
    public static Application getInstance() {
        if (sInstance == null) {
            throw new NullPointerException("please inherit BaseApplication or call setApplication.");
        }
        return sInstance;
    }

    /**
     * 初始化 DialogV3
     */
    private static void initDialogV3() {
        DialogSettings.init();

        DialogSettings.style = (DialogSettings.STYLE.STYLE_IOS);          //全局主题风格，提供三种可选风格，STYLE_MATERIAL, STYLE_KONGZUE, STYLE_IOS
        DialogSettings.theme = (DialogSettings.THEME.LIGHT);          //全局对话框明暗风格，提供两种可选主题，LIGHT, DARK
        DialogSettings.tipTheme = (DialogSettings.THEME.LIGHT);       //全局提示框明暗风格，提供两种可选主题，LIGHT, DARK

    }

    /**
     * LeakCanary 内存泄漏
     * CrashReport 初始化Bugly
     */
    private static void initBugly() {
        if (!BuildConfig.DEBUG) {
            // LeakCanary 内存泄漏
            //        if (LeakCanary.isInAnalyzerProcess(application)) {
            //            // This process is dedicated to LeakCanary for heap analysis.
            //            // You should not init your app in this process.
            //            return;
            //        }
            //        LeakCanary.install(application);
            //         Normal app init code...


            //  =================Bugly==================
            // 获取当前包名
            String packageName = sInstance.getPackageName();
            // 获取当前进程名
            String processName = getProcessName(android.os.Process.myPid());
            // 设置是否为上报进程
            UserStrategy strategy = new UserStrategy(sInstance);
            strategy.setUploadProcess(processName == null || processName.equals(packageName));
            /*
              初始化Bugly
                第三个参数为SDK调试模式开关，调试模式的行为特性如下：
                输出详细的Bugly SDK的Log；
                每一条Crash都会被立即上报；
                自定义日志将会在Logcat中输出。
                建议在测试阶段建议设置成true，发布时设置为false。
             */
            CrashReport.initCrashReport(sInstance, "2b7d15a3c8", false, strategy);
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}

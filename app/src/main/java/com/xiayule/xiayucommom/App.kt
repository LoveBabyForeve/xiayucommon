package com.xiayule.xiayucommom

import android.os.Process
import android.text.TextUtils
import com.iflytek.cloud.Setting
import com.iflytek.cloud.SpeechUtility
import com.kongzue.dialog.util.BaseDialog
import com.kongzue.dialog.util.DialogSettings
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import com.xiayule.commonlibrary.base.BaseApplication
import com.xiayule.commonlibrary.utlis.KLog
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2021-06-11 14:55
 * @UpdateUser: 更新者
 * @UpdateDate:  2021-06-11 14:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        // log初始化
        KLog.init(BuildConfig.DEBUG)

        // 初始化 DiaLogV3
        // 初始化 DiaLogV3
        initDialogV3()

        // 初始化 内存泄漏 腾讯bugly

        // 初始化 内存泄漏 腾讯bugly
        initBugly()


        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id))

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        Setting.setShowLog(true)
    }

    /**
     * 初始化 DialogV3
     */
    private fun initDialogV3() {
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS //全局主题风格，提供三种可选风格，STYLE_MATERIAL, STYLE_KONGZUE, STYLE_IOS
        DialogSettings.theme = DialogSettings.THEME.LIGHT //全局对话框明暗风格，提供两种可选主题，LIGHT, DARK
        DialogSettings.tipTheme = DialogSettings.THEME.LIGHT //全局提示框明暗风格，提供两种可选主题，LIGHT, DARK
        DialogSettings.init() //初始化清空 BaseDialog 队列
    }

    /**
     * LeakCanary 内存泄漏
     * CrashReport 初始化Bugly
     */
    private fun initBugly() {
        if (!com.xiayule.commonlibrary.BuildConfig.DEBUG) {
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
            val packageName = baseContext.packageName
            // 获取当前进程名
            val processName = getProcessName(Process.myPid())
            // 设置是否为上报进程
            val strategy = UserStrategy(baseContext)
            strategy.isUploadProcess = processName == null || processName == packageName
            /*
              初始化Bugly
                第三个参数为SDK调试模式开关，调试模式的行为特性如下：
                输出详细的Bugly SDK的Log；
                每一条Crash都会被立即上报；
                自定义日志将会在Logcat中输出。
                建议在测试阶段建议设置成true，发布时设置为false。
             */CrashReport.initCrashReport(baseContext, "a7e554980e", false, strategy)
        }
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }

    override fun onTerminate() {
        BaseDialog.unload()
        super.onTerminate()
    }
}
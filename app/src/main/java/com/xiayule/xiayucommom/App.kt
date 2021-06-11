package com.xiayule.xiayucommom

import com.xiayule.commonlibrary.base.BaseApplication
import com.xiayule.commonlibrary.utlis.KLog

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
        KLog.init(BuildConfig.DEBUG)
    }
}
package com.xiayule.commonlibrary.base;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2021-04-23 14:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-04-23 14:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IModel {
    /**
     * ViewModel销毁时清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
     */
    void onCleared();
}

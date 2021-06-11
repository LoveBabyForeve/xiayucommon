package com.xiayule.commonlibrary.base;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @Description: ViewModel声明周期
 * @Author: 下雨了
 * @CreateDate: 2021-04-23 14:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-04-23 14:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface IBaseViewModel extends LifecycleObserver {

    /**
     * 创建
     * Constant for onCreate event of the {@link LifecycleOwner}.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    /**
     * 开始
     * Constant for onStart event of the {@link LifecycleOwner}.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart();

    /**
     * 重新开始
     * Constant for onResume event of the {@link LifecycleOwner}.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    /**
     * 暂停
     * Constant for onPause event of the {@link LifecycleOwner}.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause();

    /**
     * 停止
     * Constant for onStop event of the {@link LifecycleOwner}.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop();

    /**
     * 摧毁
     * Constant for onDestroy event of the {@link LifecycleOwner}.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy();

    /**
     * 所有
     * An {@link Lifecycle.Event Event} constant that can be used to match all events.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    void onAny();

    /**
     * 注册RxBus
     */
    void registerRxBus();

    /**
     * 移除RxBus
     */
    void removeRxBus();
}

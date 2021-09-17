package com.xiayule.commonlibrary.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2021-04-13 16:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-04-13 16:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseViewModel extends AndroidViewModel implements IBaseViewModel {

    private Context mContext;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mContext = application;
    }

    @Override
    public void onCreate() {
        registerRxBus();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {
        removeRxBus();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    /**
     * 注册RxBus事件
     */
    @Override
    public void registerRxBus() {

    }

    /**
     * 移除RxBus
     */
    @Override
    public void removeRxBus() {

    }
}

package com.xiayule.commonlibrary.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

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
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {

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

    }

    @Override
    public void onAny() {

    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }
}

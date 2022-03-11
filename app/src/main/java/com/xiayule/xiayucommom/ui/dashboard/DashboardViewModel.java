package com.xiayule.xiayucommom.ui.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.xiayule.commonlibrary.base.BaseViewModel;

public class DashboardViewModel extends BaseViewModel {

    private final MutableLiveData<String> mText;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}
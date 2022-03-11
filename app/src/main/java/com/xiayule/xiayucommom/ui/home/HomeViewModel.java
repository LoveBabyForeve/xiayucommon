package com.xiayule.xiayucommom.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.xiayule.commonlibrary.base.BaseViewModel;

public class HomeViewModel extends BaseViewModel {

    private final MutableLiveData<String> mText;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}
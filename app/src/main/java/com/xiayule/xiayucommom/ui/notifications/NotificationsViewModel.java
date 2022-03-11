package com.xiayule.xiayucommom.ui.notifications;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.xiayule.commonlibrary.base.BaseViewModel;

public class NotificationsViewModel extends BaseViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}
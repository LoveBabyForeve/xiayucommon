package com.xiayule.xiayucommom.ui.picture;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.xiayule.commonlibrary.base.BaseViewModel;

public class PictureSelectorViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public PictureSelectorViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("测试数据");
    }

    public MutableLiveData<String> getmText() {
        return mText;
    }
}
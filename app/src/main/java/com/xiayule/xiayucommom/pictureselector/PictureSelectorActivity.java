package com.xiayule.xiayucommom.pictureselector;

import android.os.Bundle;

import com.xiayule.commonlibrary.base.BaseActivity;
import com.xiayule.xiayucommom.R;

public class PictureSelectorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PictureSelectorFragment.newInstance())
                    .commitNow();
        }

    }

    @Override
    public int initContentView() {
        return R.layout.activity_picture_selector;
    }

    @Override
    public void initView() {

    }
}
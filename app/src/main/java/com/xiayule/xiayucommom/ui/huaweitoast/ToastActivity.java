package com.xiayule.xiayucommom.ui.huaweitoast;

import android.view.View;
import android.widget.Button;

import com.xiayule.commonlibrary.base.BaseActivity;
import com.xiayule.commonlibrary.utlis.ToastUtils;
import com.xiayule.xiayucommom.R;

public class ToastActivity extends BaseActivity {
    Button button6;

    @Override
    public int initContentView() {
        return R.layout.activity_toast;
    }

    @Override
    public void initView() {
        button6 = findViewById(R.id.button6);
    }

    @Override
    public void initListener() {
         button6.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ToastUtils.showLong("测试");
             }
         });
    }
}
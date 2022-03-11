package com.xiayule.xiayucommom.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.xiayule.commonlibrary.base.BaseFragment;
import com.xiayule.xiayucommom.R;
import com.xiayule.xiayucommom.ui.huaweitoast.ToastActivity;

public class HomeFragment extends BaseFragment {

    TextView textView;
    HomeViewModel homeViewModel;
    private AppCompatButton button;

    @Override
    public int initContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        textView = findViewById(R.id.text_home);
        button = findViewById(R.id.button8);
    }

    @Override
    public void initViewModel() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                startActivity(ToastActivity.class, Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
    }

    @Override
    public void initViewObservable() {
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    }
}
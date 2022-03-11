package com.xiayule.xiayucommom.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.xiayule.commonlibrary.base.BaseFragment;
import com.xiayule.xiayucommom.R;

public class HomeFragment extends BaseFragment {

    TextView textView;
    HomeViewModel homeViewModel;

    @Override
    public int initContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        textView = findViewById(R.id.text_home);
    }

    @Override
    public void initViewModel() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void initViewObservable() {
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
    }
}
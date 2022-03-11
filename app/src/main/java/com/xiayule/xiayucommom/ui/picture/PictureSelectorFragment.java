package com.xiayule.xiayucommom.ui.picture;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.xiayule.commonlibrary.base.BaseFragment;
import com.xiayule.xiayucommom.R;

public class PictureSelectorFragment extends BaseFragment {

    PictureSelectorViewModel mViewModel;

    public static PictureSelectorFragment newInstance() {
        return new PictureSelectorFragment();
    }

    @Override
    public int initContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_picture_selector;
    }

    @Override
    public void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(PictureSelectorViewModel.class);
    }
}
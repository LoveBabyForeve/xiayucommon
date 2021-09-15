package com.xiayule.xiayucommom.pictureselector;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiayule.xiayucommom.R;

public class PictureSelectorFragment extends Fragment {

    private PictureSelectorViewModel mViewModel;

    public static PictureSelectorFragment newInstance() {
        return new PictureSelectorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture_selector, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PictureSelectorViewModel.class);
        // TODO: Use the ViewModel
    }

}
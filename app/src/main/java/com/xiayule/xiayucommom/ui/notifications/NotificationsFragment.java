package com.xiayule.xiayucommom.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.xiayule.commonlibrary.base.BaseFragment;
import com.xiayule.xiayucommom.R;

public class NotificationsFragment extends BaseFragment {

    NotificationsViewModel notificationsViewModel;
    TextView textView;

    @Override
    public int initContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_notifications;
    }

    @Override
    public void initView() {
        textView = findViewById(R.id.text_notifications);
    }

    @Override
    public void initViewModel() {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
    }

    @Override
    public void initViewObservable() {
        notificationsViewModel.getText().observe(this, text -> textView.setText(text));
    }


}

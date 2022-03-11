package com.xiayule.xiayucommom.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xiayule.commonlibrary.base.BaseFragment;
import com.xiayule.xiayucommom.R;

public class DashboardFragment extends BaseFragment {

    DashboardViewModel dashboardViewModel;
    TextView textView;

    AppCompatButton button;
    ImageView imageView;

    String normalUrl = "http://www.abc.szzfyjzx.icu/icon6.png";
    String pressUrl = "http://www.abc.szzfyjzx.icu/icon7.png";


    @Override
    public int initContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void initView() {
        textView = findViewById(R.id.text_dashboard);
        button = findViewById(R.id.button7);
        imageView = findViewById(R.id.imageView3);
    }

    @Override
    public void initViewModel() {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
    }

    @Override
    public void initViewObservable() {
        dashboardViewModel.getText().observe(this, text -> textView.setText(text));
    }

    @Override
    public void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SelectorUtils.addSeletorFromNet(requireContext().getClass(), normalUrl, pressUrl, button);

                final StateListDrawable drawable = new StateListDrawable();
                Glide.with(requireContext()).asBitmap().load(normalUrl).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        Drawable newDraw = new BitmapDrawable(bitmap);
                        drawable.addState(new int[]{-android.R.attr.state_pressed}, newDraw);


                        Glide.with(requireContext()).asBitmap().load(pressUrl).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Drawable newDraw = new BitmapDrawable(resource);
                                drawable.addState(new int[]{android.R.attr.state_pressed}, newDraw);
                                imageView.setBackground(drawable);
                            }
                        });
                    }
                });


            }
        });
    }
}
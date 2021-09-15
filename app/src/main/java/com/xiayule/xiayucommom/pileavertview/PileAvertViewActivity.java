package com.xiayule.xiayucommom.pileavertview;

import androidx.lifecycle.MutableLiveData;

import com.xiayule.commonlibrary.base.BaseActivity;
import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy;
import com.xiayule.commonlibrary.widget.NiceImageView;
import com.xiayule.xiayucommom.PileAvertView;
import com.xiayule.xiayucommom.R;

import java.util.ArrayList;

public class PileAvertViewActivity extends BaseActivity {


    public NiceImageView mNiceimageview;
    public PileAvertView mPileavertview;

    @Override
    public int initContentView() {
        return R.layout.activity_pile_avert_view;
    }

    @Override
    public void initView() {
        initViews();
    }

    private void initViews() {
        mNiceimageview = findViewById(R.id.niceImageView);
        mPileavertview = findViewById(R.id.pileAvertView);
    }

    @Override
    public void initData() {
        ImageLoaderProxy.getInstance().displayImages("https://cdn.pixabay.com/photo/2018/05/23/22/37/chinchillas-3425370_960_720.jpg", mNiceimageview);


        MutableLiveData<String> mutableList = new MutableLiveData();

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("https://cdn.pixabay.com/photo/2018/05/23/22/37/chinchillas-3425370_960_720.jpg");
        }
        mPileavertview.setAvertImages(list, 4);
    }
}
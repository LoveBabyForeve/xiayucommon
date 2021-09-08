package com.xiayule.xiayucommom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy;
import com.xiayule.commonlibrary.widget.NiceImageView;
import com.xiayule.commonlibrary.widget.PileView;

import java.util.List;

/**
 * @Description: 在 {@link PileView} 控件的基础上再次封装 用于应用
 * @Author: 下雨了
 * @CreateDate: 2021-09-07 15:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-09-07 15:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PileAvertView extends LinearLayout {

    public static final int VISIBLE_COUNT = 3;//默认显示个数

    private PileView mPileView;

    public PileAvertView(Context context) {
        super(context);
        initView();
    }

    public PileAvertView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PileAvertView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_group_pile_avert, this);
        mPileView = view.findViewById(R.id.pile_view);
        mPileView.setFlag(false);
        mPileView.setPileWidth(30f);
        mPileView.setVertivalSpace(30f);
    }

    public void setAvertImages(List<String> imageList) {
        setAvertImages(imageList, VISIBLE_COUNT);
    }

    public void setAvertImages(List<String> imageList, int visibleCount) {
//        List<String> visibleList = null;
//        if (imageList.size() > visibleCount) {
//            visibleList = imageList.subList(imageList.size() - 1 - visibleCount, imageList.size() - 1);
//        }
//        mPileView.removeAllViews();
        for (int i = 0; i < imageList.size(); i++) {
            NiceImageView niceImageView = (NiceImageView) LayoutInflater.from(getContext()).inflate(R.layout.item_group_round_avert, mPileView, false);
            ImageLoaderProxy.getInstance().displayImages(imageList.get(i), niceImageView);
            mPileView.addView(niceImageView);
        }
    }
}

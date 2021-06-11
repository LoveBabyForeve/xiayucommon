package com.xiayule.commonlibrary.imageLoader;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xiayule.commonlibrary.R;

import java.io.File;


/**
 * @Description: Glide图片加载器
 * @Author: 下雨了
 * @CreateDate: 2019/12/16 10:21
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/16 10:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GlideImageLoad implements ImageLoader {

    private Context mContext;

    @Override
    public void init(Context context) {
        this.mContext = context;
    }

    /**
     * @param imageUrl     Url地址
     * @param imageView    显示图片的控件
     * @param defaultImage 默认图片
     */
    @Override
    public void displayImages(String imageUrl, ImageView imageView, @Nullable int defaultImage) {
        //使用Glide框架加载图片
        Glide.with(mContext)
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(defaultImage))
                .into(imageView);
    }


    /**
     * 显示图片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    @Override
    public void displayImages(String imageUrl, ImageView imageView) {
        Glide.with(mContext)
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(mContext.getResources().getDrawable(R.drawable.image_error)))
                .into(imageView);
    }

    /**
     * 显示图片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    @Override
    public void displayImages(File imageUrl, ImageView imageView) {
        Glide.with(mContext)
                .load(imageUrl)
                .apply(new RequestOptions().placeholder(mContext.getResources().getDrawable(R.drawable.image_error)))
                .into(imageView);
    }

    /**
     * 显示本地照片
     *
     * @param imageUrl     Url地址
     * @param imageView    显示图片的控件
     */
    @Override
    public void displayImages(int imageUrl, ImageView imageView) {
        //使用Glide框架加载图片
        Glide.with(mContext)
                .load(imageUrl)
//                .apply(new RequestOptions().placeholder(mContext.getResources().getDrawable(R.drawable.image_error)))
                .into(imageView);
    }

    /**
     * 显示圆形图片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    @Override
    public void displayRoundedImages(String imageUrl, ImageView imageView) {
        Glide.with(mContext)
                .load(imageUrl)
                .apply(new RequestOptions().circleCrop())
                .into(imageView);

    }
}

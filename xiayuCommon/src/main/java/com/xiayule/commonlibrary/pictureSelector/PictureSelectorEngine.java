package com.xiayule.commonlibrary.pictureSelector;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.listener.OnImageCompleteCallback;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy;

/**
 * @Description: PictureSelector 图片 加载引擎（本项目使用 Glide）
 * @Author: 下雨了
 * @CreateDate: 2021-04-19 14:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-04-19 14:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PictureSelectorEngine implements ImageEngine {

    private static PictureSelectorEngine instance;

    /**
     * 单例
     */
    public static PictureSelectorEngine getInstance() {
        if (instance == null) {
            synchronized (PictureSelectorEngine.class) {
                if (instance == null) {
                    instance = new PictureSelectorEngine();
                }
            }
        }
        return instance;
    }


    /**
     * 加载图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        ImageLoaderProxy.getInstance().displayImages(url, imageView);
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @param callback      网络图片加载回调监听 {link after version 2.5.1 Please use the #OnImageCompleteCallback#}
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView, OnImageCompleteCallback callback) {
        ImageLoaderProxy.getInstance().displayImages(url, imageView);
    }

    /**
     * 加载网络图片适配长图方案
     * # 注意：此方法只有加载网络图片才会回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param longImageView
     * @ 已废弃
     */
    @Override
    public void loadImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView, SubsamplingScaleImageView longImageView) {
        ImageLoaderProxy.getInstance().displayImages(url, imageView);
    }

    /**
     * 加载相册目录
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadFolderImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {

    }

    /**
     * 加载gif
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadAsGifImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        ImageLoaderProxy.getInstance().displayImages(url, imageView);
    }

    /**
     * 加载图片列表图片
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 承载图片ImageView
     */
    @Override
    public void loadGridImage(@NonNull Context context, @NonNull String url, @NonNull ImageView imageView) {
        ImageLoaderProxy.getInstance().displayImages(url, imageView);

    }
}

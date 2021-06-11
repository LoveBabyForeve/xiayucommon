package com.xiayule.commonlibrary.imageLoader;

import android.widget.ImageView;

import java.io.File;

/**
 * @Description: 图片加载器
 * @Author: 下雨了
 * @CreateDate: 2019/12/16 10:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/16 10:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface ImageLoader extends IInitialize {

    /**
     * 自选默认图
     *
     * @param imageUrl     Url地址
     * @param imageView    显示图片的控件
     * @param defaultImage 默认图片
     */
    void displayImages(String imageUrl, ImageView imageView, int defaultImage);

    /**
     * 显示图片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    void displayImages(String imageUrl, ImageView imageView);

    void displayImages(File imageUrl, ImageView imageView);

    /**
     * 显示本地照片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    void displayImages(int imageUrl, ImageView imageView);

    /**
     * 显示圆形图片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    void displayRoundedImages(String imageUrl, ImageView imageView);
}

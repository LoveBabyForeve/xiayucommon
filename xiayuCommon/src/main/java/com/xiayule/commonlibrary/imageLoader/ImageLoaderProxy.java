package com.xiayule.commonlibrary.imageLoader;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;


/**
 * @Description: 图片加载器代理对象
 * @Author: 下雨了
 * @CreateDate: 2019/12/16 10:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/12/16 10:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */


/**
 * 调用示例
 * String imageUrl="http://images2015.cnblogs.com/news/24442/201703/24442-20170301190900595-1906438193.jpg";
 * ImageView imageView = (ImageView) findViewById(R.id.iv_test);
 * ImageLoaderProxy.getInstance().displayImages(imageUrl, imageView, R.mipmap.ic_default);
 */


public class ImageLoaderProxy implements ImageLoader {

    private ImageLoader imageLoader;

    private static volatile ImageLoaderProxy instance;

    public static ImageLoaderProxy getInstance() {
        if (instance == null) {
            synchronized (ImageLoaderProxy.class) {
                if (instance == null) {
                    instance = new ImageLoaderProxy();
                }
            }
        }
        return instance;
    }

    public ImageLoaderProxy() {
        //可任意替换
        imageLoader = new GlideImageLoad();
//        imageLoader = new UniversalImageLoader();
//        init(DataProviderManager.getContext());

    }

    @Override
    public void init(Context context) {
        imageLoader.init(context);
    }

    /**
     * 自选默认图
     *
     * @param imageUrl     Url地址
     * @param imageView    显示图片的控件
     * @param defaultImage 默认图片
     */
    public void displayImages(String imageUrl, ImageView imageView, int defaultImage) {
        imageLoader.displayImages(imageUrl, imageView, defaultImage);
    }

    /**
     * 显示图片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    @Override
    public void displayImages(String imageUrl, ImageView imageView) {
        imageLoader.displayImages(imageUrl, imageView);
    }

    @Override
    public void displayImages(File imageUrl, ImageView imageView) {
        imageLoader.displayImages(imageUrl, imageView);
    }

    /**
     * 显示本地照片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    @Override
    public void displayImages(int imageUrl, ImageView imageView) {
        imageLoader.displayImages(imageUrl, imageView);
    }

    /**
     * 显示圆形图片
     *
     * @param imageUrl  Url地址
     * @param imageView 显示图片的控件
     */
    @Override
    public void displayRoundedImages(String imageUrl, ImageView imageView) {
        imageLoader.displayRoundedImages(imageUrl, imageView);
    }
}

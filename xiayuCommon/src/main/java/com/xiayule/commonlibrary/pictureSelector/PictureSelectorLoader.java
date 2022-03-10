package com.xiayule.commonlibrary.pictureSelector;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.app.IApp;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xiayule.commonlibrary.R;
import com.xiayule.commonlibrary.utlis.Utils;

import java.util.List;

/**
 * @Description: PictureSelector代理
 * @Author: 下雨了
 * @CreateDate: 2021-04-20 11:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-04-20 11:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PictureSelectorLoader {

    private static PictureSelectorLoader instance;


    // 默认选择最大数量
    public int mMaxNumber = 9;

    /**
     * 构造函数
     */
    private PictureSelectorLoader() {

        // 初始化 PictureSelector
        PictureAppMaster.getInstance().setApp(new IApp() {

            @Override
            public Context getAppContext() {
                return Utils.getContext();
            }

            @Override
            public com.luck.picture.lib.engine.PictureSelectorEngine getPictureSelectorEngine() {
                return new PictureSelectorEngineImp();
            }
        });
    }

    /**
     * 单例
     */
    public static synchronized PictureSelectorLoader getInstance() {
        if (instance == null) {
            instance = new PictureSelectorLoader();
        }
        return instance;
    }


    /**
     * 创建 只有图片
     *
     * @param activity
     */
    public void createImage(Activity activity) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(PictureSelectorEngine.getInstance()) // 请参考Demo GlideEngine.java
//                .theme(R.style.picture_default_style) // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
//                .setPictureStyle(PictureSelectorStyle.getInstance().initPictureUIParam())// 动态自定义相册主题
                .setPictureUIStyle(PictureSelectorStyle.getInstance().initPictureUIStyle())
                .setPictureWindowAnimationStyle(PictureSelectorStyle.getInstance().initOpenWindowAnimat()) // 自定义相册启动退出动画
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .imageSpanCount(4)// 每行显示个数
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .withAspectRatio(3, 4)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(true)// 是否开启点击声音

                .maxSelectNum(mMaxNumber) // 最大图片选择数量
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 创建 只有图片
     *
     * @param fragment
     */
    public void createImage(Fragment fragment) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage()) //// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(PictureSelectorEngine.getInstance()) // 请参考Demo GlideEngine.java
//                .theme(R.style.picture_default_style) // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
//                .setPictureStyle(PictureSelectorStyle.getInstance().initPictureUIParam())// 动态自定义相册主题
                .setPictureUIStyle(PictureSelectorStyle.getInstance().initPictureUIStyle())
                .setPictureWindowAnimationStyle(PictureSelectorStyle.getInstance().initOpenWindowAnimat()) // 自定义相册启动退出动画
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .imageSpanCount(4)// 每行显示个数
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .withAspectRatio(3, 4)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(true)// 是否开启点击声音

                .maxSelectNum(mMaxNumber) // 最大图片选择数量
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 创建 所有
     *
     * @param activity
     */
    public void createAll(Activity activity) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofAll())
                .imageEngine(PictureSelectorEngine.getInstance()) // 请参考Demo GlideEngine.java
//                .theme(R.style.picture_default_style) // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
//                .setPictureStyle(PictureSelectorStyle.getInstance().initPictureUIParam())// 动态自定义相册主题
                .setPictureUIStyle(PictureSelectorStyle.getInstance().initPictureUIStyle())
                .setPictureWindowAnimationStyle(PictureSelectorStyle.getInstance().initOpenWindowAnimat()) // 自定义相册启动退出动画
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .imageSpanCount(4)// 每行显示个数
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .withAspectRatio(3, 4)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(true)// 是否开启点击声音

                .maxSelectNum(mMaxNumber) // 最大图片选择数量
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 创建 所有
     *
     * @param fragment
     */
    public void createAll(Fragment fragment) {
        PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofAll())
                .imageEngine(PictureSelectorEngine.getInstance()) // 请参考Demo GlideEngine.java
//                .theme(R.style.picture_default_style) // 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
//                .setPictureStyle(PictureSelectorStyle.getInstance().initPictureUIParam())// 动态自定义相册主题
                .setPictureUIStyle(PictureSelectorStyle.getInstance().initPictureUIStyle())
                .setPictureWindowAnimationStyle(PictureSelectorStyle.getInstance().initOpenWindowAnimat()) // 自定义相册启动退出动画
                .isWeChatStyle(false)// 是否开启微信图片选择风格
                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
                .imageSpanCount(4)// 每行显示个数
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .withAspectRatio(3, 4)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .isOpenClickSound(true)// 是否开启点击声音

                .maxSelectNum(mMaxNumber) // 最大图片选择数量
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 跳转到图片预览界面
     *
     * @param activity
     * @param position    点击的当前 position
     * @param mSelectList 当前集合
     */
    public void preview(Activity activity, int position, List<LocalMedia> mSelectList) {
        PictureSelector.create(activity)
                .themeStyle(com.luck.picture.lib.R.style.picture_default_style)

                .setPictureStyle(PictureSelectorStyle.getInstance().initPictureUIParam())// 动态自定义相册主题

                .isNotPreviewDownload(true)
                .imageEngine(PictureSelectorEngine.getInstance()) // 请参考Demo GlideEngine.java
                .openExternalPreview(position, mSelectList);
    }

    /**
     * 跳转到图片预览界面
     *
     * @param fragment
     * @param position    点击的当前 position
     * @param mSelectList 当前集合
     */
    public void preview(Fragment fragment, int position, List<LocalMedia> mSelectList) {
        PictureSelector.create(fragment)
                .themeStyle(com.luck.picture.lib.R.style.picture_default_style)

                .setPictureStyle(PictureSelectorStyle.getInstance().initPictureUIParam())// 动态自定义相册主题

                .isNotPreviewDownload(true)
                .imageEngine(PictureSelectorEngine.getInstance()) // 请参考Demo GlideEngine.java
                .openExternalPreview(position, mSelectList);
    }
    
    
    // ================================ ==================================

}

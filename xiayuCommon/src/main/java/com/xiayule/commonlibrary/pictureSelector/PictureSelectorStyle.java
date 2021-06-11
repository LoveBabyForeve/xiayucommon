package com.xiayule.commonlibrary.pictureSelector;


import android.content.Context;
import android.graphics.Color;

import com.luck.picture.lib.app.IApp;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.style.PictureSelectorUIStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.tools.ScreenUtils;
import com.xiayule.commonlibrary.R;

/**
 * @Description: 自定义 PictureSelector Style
 * @Author: 下雨了
 * @CreateDate: 2021-04-20 10:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-04-20 10:53
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PictureSelectorStyle {

    private static PictureSelectorStyle instace;

    /**
     * 单例
     */
    public static synchronized PictureSelectorStyle getInstance() {
        if (instace == null) {
            instace = new PictureSelectorStyle();
        }
        return instace;
    }

    /**
     * 初始化 打开相册窗口 动画
     * <p>
     * 动画：从下到上
     */
    public PictureWindowAnimationStyle initOpenWindowAnimat() {

        return PictureWindowAnimationStyle.ofCustomWindowAnimationStyle(R.anim.picture_anim_up_in, R.anim.picture_anim_down_out);

    }

    /**
     * 初始化 打开相册 UI 样式
     */
    public PictureSelectorUIStyle initPictureUIStyle() {
        PictureSelectorUIStyle uiStyle = new PictureSelectorUIStyle();
        /**
         * 状态栏背景色(显示时间 显示wifi)
         */
        uiStyle.picture_statusBarBackgroundColor = Color.parseColor("#000000");
        /**
         * NavBarColor (显示返回按钮)
         */
        uiStyle.picture_navBarColor = Color.parseColor("#FFFFFF");

        /**
         * 标题栏背景色
         */
        uiStyle.picture_top_titleBarBackgroundColor = Color.parseColor("#187CEC");
        /**
         * 相册背景色
         */
        uiStyle.picture_container_backgroundColor = Color.parseColor("#FFF8F9FB");
        /**
         * 底部bar背景色
         */
        uiStyle.picture_bottom_barBackgroundColor = Color.parseColor("#187CEC");
        // ********************************************************************************************************

        /**
         * 是否开启数字风格选择模式
         */
        uiStyle.picture_switchSelectNumberStyle = true;
        /**
         * 选中样式
         */
        uiStyle.picture_check_style = R.drawable.picture_checkbox_num_selector;

        // ********************************************************************************************************


        /**
         * 返回按钮
         */
        uiStyle.picture_top_leftBack = com.luck.picture.lib.R.drawable.picture_icon_back;

        /**
         * 标题栏右侧按钮正常文案(可点击)
         */
        uiStyle.picture_top_titleRightNormalText = R.string.picture_cancel;
        /**
         * 标题栏右侧按钮默认文案
         */
        uiStyle.picture_top_titleRightDefaultText = R.string.picture_cancel;
        /**
         * 标题栏右侧文字大小
         */
        uiStyle.picture_top_titleRightTextSize = 14;
        /**
         * 标题栏右侧文字颜色
         */
        uiStyle.picture_top_titleRightTextColor = new int[]{Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF")};


        /**
         * 标题字体大小
         */
        uiStyle.picture_top_titleTextSize = 18;
        /**
         * 标题字体颜色
         */
        uiStyle.picture_top_titleTextColor = Color.parseColor("#FFFFFF");
        /**
         * 标题右侧向上箭头
         */
        uiStyle.picture_top_titleArrowUpDrawable = com.luck.picture.lib.R.drawable.picture_icon_arrow_up;
        /**
         * 标题右侧向下箭头
         */
        uiStyle.picture_top_titleArrowDownDrawable = com.luck.picture.lib.R.drawable.picture_icon_arrow_down;

        /**
         * 专辑item背景
         */
        uiStyle.picture_album_backgroundStyle = com.luck.picture.lib.R.drawable.picture_item_select_bg;
        /**
         * 专辑字体大小
         */
        uiStyle.picture_album_textSize = 16;
        /**
         * 专辑字体颜色
         */
        uiStyle.picture_album_textColor = Color.parseColor("#4d4d4d");
        /**
         * 专辑选中提醒样式
         */
        uiStyle.picture_album_checkDotStyle = R.drawable.picture_num_oval_blue;


        // ********************************************************************************************************


        /**
         * 底部 预览 文字(可点击)
         */
        uiStyle.picture_bottom_previewNormalText = R.string.picture_preview_num;
        /**
         * 底部预览默认文字
         */
        uiStyle.picture_bottom_previewDefaultText = R.string.picture_preview;
        /**
         * 底部预览文字大小
         */
        uiStyle.picture_bottom_previewTextSize = 14;
        /**
         * 底部预览文字颜色
         */
        uiStyle.picture_bottom_previewTextColor = new int[]{Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF")};


        /**
         * 完成正常文案(可点击)
         */
        uiStyle.picture_bottom_completeNormalText = R.string.picture_completed;
        /**
         * 完成默认文案
         */
        uiStyle.picture_bottom_completeDefaultText = R.string.picture_please_select;
        /**
         * 完成文字大小
         */
        uiStyle.picture_bottom_completeTextSize = 14;
        /**
         * 完成文字字体颜色
         */
        uiStyle.picture_bottom_completeTextColor = new int[]{Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF")};

        /**
         * 完成红点字体大小
         */
        uiStyle.picture_bottom_completeRedDotTextSize = 12;
        /**
         * 完成红点字体颜色
         */
        uiStyle.picture_bottom_completeRedDotTextColor = Color.parseColor("#FFFFFF");
        /**
         * 完成红点背景色
         */
        uiStyle.picture_bottom_completeRedDotBackground = R.drawable.picture_num_oval_blue;


        // ********************************************************************************************************


        /**
         * adapter 拍照文案
         */
        uiStyle.picture_adapter_item_camera_text = R.string.picture_take_picture;
        /**
         * adapter 拍照item背景色
         */
        uiStyle.picture_adapter_item_camera_backgroundColor = Color.parseColor("#999999");
        /**
         * adapter 拍照文字颜色
         */
        uiStyle.picture_adapter_item_camera_textColor = Color.parseColor("#FFFFFF");
        /**
         * adapter 拍照文字大小
         */
        uiStyle.picture_adapter_item_camera_textSize = 14;
        /**
         * adapter 拍照 top drawable
         */
        uiStyle.picture_adapter_item_camera_textTopDrawable = com.luck.picture.lib.R.drawable.picture_icon_camera;
        /**
         * adapter item 文字大小
         */
        uiStyle.picture_adapter_item_textSize = 12;
        /**
         * adapter item 文字颜色
         */
        uiStyle.picture_adapter_item_textColor = Color.parseColor("#FFFFFF");
        /**
         * adapter item 文字left drawable
         */
        uiStyle.picture_adapter_item_video_textLeftDrawable = com.luck.picture.lib.R.drawable.picture_icon_video;
        /**
         * adapter item 文字left drawable
         */
        uiStyle.picture_adapter_item_audio_textLeftDrawable = com.luck.picture.lib.R.drawable.picture_icon_audio;


        // ********************************************************************************************************

        /**
         * 原图文案
         */
        uiStyle.picture_bottom_originalPictureText = R.string.picture_original_image;
        /**
         * 原图文字大小
         */
        uiStyle.picture_bottom_originalPictureTextSize = 14;
        /**
         * 原图勾选样式
         */
        uiStyle.picture_bottom_originalPictureCheckStyle = com.luck.picture.lib.R.drawable.picture_original_blue_checkbox;
        /**
         * 原图文字颜色
         */
        uiStyle.picture_bottom_originalPictureTextColor = Color.parseColor("#FFFFFF");


        IApp app = PictureAppMaster.getInstance().getApp();
        Context appContext = app.getAppContext();
        if (appContext != null) {
            /**
             * 标题栏高度
             */
            uiStyle.picture_top_titleBarHeight = ScreenUtils.dip2px(appContext, 48);
            /**
             * 底部bar高度
             */
            uiStyle.picture_bottom_barHeight = ScreenUtils.dip2px(appContext, 45);
            /**
             * 是否使用(%1$d/%2$d)字符串 如果文本内容设置(%1$d/%2$d)，请开启true
             */
            uiStyle.isCompleteReplaceNum = true;
        }
        return uiStyle;
    }

    /**
     * 初始化 打开相册 UI 参数
     */
    public PictureParameterStyle initPictureUIParam() {
        PictureParameterStyle mPictureParameterStyle = new PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
//        mPictureParameterStyle.isChangeStatusBarFontColor = false;

        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#000000");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#187CEC");
        mPictureParameterStyle.pictureNavBarColor = Color.parseColor("#FFFFFF");


        return mPictureParameterStyle;
    }
}

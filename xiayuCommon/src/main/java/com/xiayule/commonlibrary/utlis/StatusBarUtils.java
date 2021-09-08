package com.xiayule.commonlibrary.utlis;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.core.graphics.ColorUtils;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2021-09-07 14:17
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-09-07 14:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class StatusBarUtils {

    private static StatusBarUtils mStatusBarUtils;

    public static synchronized StatusBarUtils getInstance() {
        if (mStatusBarUtils == null) {
            mStatusBarUtils = new StatusBarUtils();
        }
        return mStatusBarUtils;
    }

    /**
     * 修改状态栏为沉浸式，并修改背景颜色与字体颜色
     *
     * @param activity 当前activity
     * @param color    背景颜色
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void transparencyBar(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN设置为沉浸式
            // View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR 设置字体颜色为深色
            // View.SYSTEM_UI_FLAG_VISIBLE 设置字体颜色为浅色
            // 如果亮色，设置状态栏文字为黑色  
            if (isLightColor(color)) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
            }
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // 这里设置背景颜色，也可以设置成为透明色
            window.setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private boolean isLightColor(@ColorInt int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    protected int getStatusBarColor() {
        return Color.WHITE;
    }
}

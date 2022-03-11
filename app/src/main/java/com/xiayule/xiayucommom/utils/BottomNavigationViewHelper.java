package com.xiayule.xiayucommom.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * @Description: java类作用描述
 * @Author: 下雨了
 * @CreateDate: 2022-03-08 16:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2022-03-08 16:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BottomNavigationViewHelper {
    /**
     * 设置图片尺寸
     *
     * @param view
     * @param width
     * @param height
     */
    public static void setImageSize(BottomNavigationView view, int width, int height) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                ImageView imageView = item.findViewById(com.google.android.material.R.id.icon);
                final ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.height = width;
                layoutParams.width = height;
                imageView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Icon距离顶部高度
     * 不需要了，直接设置design_bottom_navigation_margin属性即可
     *
     * @param view
     * @param marginTop
     */
    public static void setImageMarginTop(BottomNavigationView view, int marginTop) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                ImageView imageView = item.findViewById(com.google.android.material.R.id.icon);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                layoutParams.topMargin = marginTop;
                imageView.setLayoutParams(layoutParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Icon为网络图标
     *
     * @param view
     * @param imgUrl
     */
    public static void replaceItemImage(BottomNavigationView view, String[] imgUrl) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                ImageView imageView = item.findViewById(com.google.android.material.R.id.navigation_bar_item_icon_view);
//                Glide.with(view.getContext())
//                        .load(imgUrl[i])
//                        .into(imageView);

                final StateListDrawable drawable = new StateListDrawable();
                Glide.with(view.getContext()).asBitmap().load(imgUrl[0]).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        Drawable newDraw = new BitmapDrawable(bitmap);
                        drawable.addState(new int[]{-android.R.attr.state_checked}, newDraw);


                        Glide.with(view.getContext()).asBitmap().load(imgUrl[1]).into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Drawable newDraw = new BitmapDrawable(resource);
                                drawable.addState(new int[]{android.R.attr.state_checked}, newDraw);
                                imageView.setBackground(drawable);
                            }
                        });
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Icon为刷新图标
     *
     * @param view
     * @param gifUrl
     */
    public static void replaceRefreshImage(BottomNavigationView view, int index, String gifUrl) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(index);
            ImageView imageView = item.findViewById(com.google.android.material.R.id.icon);
            Glide.with(view.getContext())
                    .load(gifUrl)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

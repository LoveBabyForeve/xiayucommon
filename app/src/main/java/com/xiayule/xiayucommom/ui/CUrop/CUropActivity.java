package com.xiayule.xiayucommom.ui.CUrop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.xiayule.commonlibrary.base.BaseActivity;
import com.xiayule.commonlibrary.utlis.ToastUtils;
import com.xiayule.xiayucommom.R;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;


public class CUropActivity extends BaseActivity {

    // 是输入图片的Uri
    Uri sourceUri = null;
    // 是输出图片的Uri
    Uri destinationUri = null;

    @Override
    public int initContentView() {
        return R.layout.activity_curop;
    }

    @Override
    public void initListener() {

        findViewById(R.id.bt_curop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("点击了");
                UCrop.of(sourceUri, destinationUri)
                        .withAspectRatio(16, 9)
                        .withMaxResultSize(320, 180)
                        .start(CUropActivity.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }


    /**
     * 启动裁剪
     *
     * @param activity       上下文
     * @param sourceFilePath 需要裁剪图片的绝对路径
     * @param requestCode    比如：UCrop.REQUEST_CROP
     * @param aspectRatioX   裁剪图片宽高比
     * @param aspectRatioY   裁剪图片宽高比
     * @return
     */
    public static String startUCrop(Activity activity, String sourceFilePath,
                                    int requestCode, float aspectRatioX, float aspectRatioY) {
        Uri sourceUri = Uri.fromFile(new File(sourceFilePath));
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
        //裁剪后图片的绝对路径
        String cameraScalePath = outFile.getAbsolutePath();
        Uri destinationUri = Uri.fromFile(outFile);
        //初始化，第一个参数：需要裁剪的图片；第二个参数：裁剪后图片
        UCrop uCrop = UCrop.of(sourceUri, destinationUri);
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(true);
        //UCrop配置
        uCrop.withOptions(options);
        //设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(aspectRatioX, aspectRatioY);

        //uCrop.useSourceImageAspectRatio();
        //跳转裁剪页面
        uCrop.start((AppCompatActivity) activity, requestCode);
        return cameraScalePath;
    }

    //设置Toolbar标题
//    void setToolbarTitle(@Nullable String text)
//    //设置裁剪的图片格式
//    void setCompressionFormat(@NonNull Bitmap.CompressFormat format)
//    //设置裁剪的图片质量，取值0-100
//    void setCompressionQuality(@IntRange(from = 0) int compressQuality)
//    //设置最多缩放的比例尺
//    void setMaxScaleMultiplier(@FloatRange(from = 1.0, fromInclusive = false) float maxScaleMultiplier)
//    //动画时间
//    void setImageToCropBoundsAnimDuration(@IntRange(from = 100) int durationMillis)
//    //设置图片压缩最大值
//    void setMaxBitmapSize(@IntRange(from = 100) int maxBitmapSize)
//    //是否显示椭圆裁剪框阴影
//    void setOvalDimmedLayer(boolean isOval)
//    //设置椭圆裁剪框阴影颜色
//    void setDimmedLayerColor(@ColorInt int color)
//    //是否显示裁剪框
//    void setShowCropFrame(boolean show)
//    //设置裁剪框边的宽度
//    void setCropFrameStrokeWidth(@IntRange(from = 0) int width)
//    //是否显示裁剪框网格
//    void setShowCropGrid(boolean show)
//    //设置裁剪框网格颜色
//    void setCropGridColor(@ColorInt int color)
//    //设置裁剪框网格宽
//    void setCropGridStrokeWidth(@IntRange(from = 0) int width)
}
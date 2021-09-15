package com.xiayule.xiayucommom

import android.graphics.Color
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.luck.picture.lib.PictureSelectorActivity
import com.xiayule.commonlibrary.base.BaseActivity
import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy
import com.xiayule.commonlibrary.utlis.StatusBarUtils
import com.xiayule.xiayucommom.pileavertview.PileAvertViewActivity

class MainActivity : BaseActivity() {

    lateinit var button: Button
    lateinit var button3: Button
    lateinit var button4: Button
    lateinit var imageView: ImageView

    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        StatusBarUtils.getInstance().transparencyBar(this, Color.TRANSPARENT)
        button = findViewById(R.id.button)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)
        imageView = findViewById(R.id.imageView)

        ImageLoaderProxy.getInstance().displayImages(
            "https://cdn.pixabay.com/photo/2018/05/23/22/37/chinchillas-3425370_960_720.jpg",
            imageView
        )

    }

    override fun initListener() {
        button.setOnClickListener {
            startActivity(PileAvertViewActivity().javaClass)
        }

        button3.setOnClickListener {
            startActivity(CUropActivity().javaClass)
        }
        button4.setOnClickListener {
            startActivity(PictureSelectorActivity().javaClass)
        }
    }
}
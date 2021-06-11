package com.xiayule.xiayucommom

import android.widget.Button
import android.widget.ImageView
import com.xiayule.commonlibrary.base.BaseActivity
import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy

class MainActivity : BaseActivity() {

    lateinit var button: Button
    lateinit var imageView: ImageView

    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        button = findViewById(R.id.button)
        imageView = findViewById(R.id.imageView)

        ImageLoaderProxy.getInstance().displayImages("https://cdn.pixabay.com/photo/2018/05/23/22/37/chinchillas-3425370_960_720.jpg", imageView)
    }

    override fun initListener() {
        button.setOnClickListener {
            startActivity(BlankActivity().javaClass)
        }
    }
}
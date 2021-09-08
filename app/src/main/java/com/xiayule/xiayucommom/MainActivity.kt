package com.xiayule.xiayucommom

import android.graphics.Color
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.xiayule.commonlibrary.base.BaseActivity
import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy
import com.xiayule.commonlibrary.utlis.StatusBarUtils
import com.xiayule.commonlibrary.widget.NiceImageView

class MainActivity : BaseActivity() {

    lateinit var button: Button
    lateinit var imageView: ImageView
    lateinit var niceImageView: NiceImageView
    lateinit var pileAvertView: PileAvertView

    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        StatusBarUtils.getInstance().transparencyBar(this, Color.TRANSPARENT)
        button = findViewById(R.id.button)
        imageView = findViewById(R.id.imageView)
        niceImageView = findViewById(R.id.niceImageView)
        pileAvertView = findViewById(R.id.pileAvertView)

        ImageLoaderProxy.getInstance().displayImages("https://cdn.pixabay.com/photo/2018/05/23/22/37/chinchillas-3425370_960_720.jpg", imageView)
        ImageLoaderProxy.getInstance().displayImages("https://cdn.pixabay.com/photo/2018/05/23/22/37/chinchillas-3425370_960_720.jpg",niceImageView)

       
    }

    override fun initListener() {
        button.setOnClickListener {
//            startActivity(BlankActivity().javaClass)

            val mutableList: MutableList<String> = mutableListOf()
            for (i in 1..10) {
                mutableList.add("https://cdn.pixabay.com/photo/2018/05/23/22/37/chinchillas-3425370_960_720.jpg")
            }
            pileAvertView.setAvertImages(mutableList, 3)
        }
    }
}
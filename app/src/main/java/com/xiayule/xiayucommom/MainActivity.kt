package com.xiayule.xiayucommom

import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.xiayule.commonlibrary.base.BaseActivity
import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy
import com.xiayule.commonlibrary.widget.NiceImageView

class MainActivity : BaseActivity() {

    lateinit var button: Button
    lateinit var imageView: ImageView
    lateinit var niceImageView: NiceImageView
    lateinit var pileAvertView: PileAvertView

    override fun initContentView(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
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
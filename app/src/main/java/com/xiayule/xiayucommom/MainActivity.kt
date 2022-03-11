package com.xiayule.xiayucommom

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.iflytek.voicedemo.SpeechMainActivity
import com.luck.picture.lib.PictureSelectorActivity
import com.xiayule.commonlibrary.base.BaseActivity
import com.xiayule.commonlibrary.imageLoader.ImageLoaderProxy
import com.xiayule.commonlibrary.utlis.StatusBarUtils
import com.xiayule.xiayucommom.ui.CUrop.CUropActivity
import com.xiayule.xiayucommom.ui.huaweitoast.ToastActivity
import com.xiayule.xiayucommom.ui.pileavertview.PileAvertViewActivity

class MainActivity : BaseActivity() {

    lateinit var button: Button
    lateinit var button3: Button
    lateinit var button4: Button
    lateinit var button5: Button
    lateinit var button6: Button
    lateinit var button7: Button
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
        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)

        ImageLoaderProxy.getInstance().displayImages(
            "https://cdn.pixabay.com/photo/2018/05/23/22/37/chinchillas-3425370_960_720.jpg",
            imageView
        )

    }

    @RequiresApi(Build.VERSION_CODES.N)
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

        button5.setOnClickListener {
            startActivity(ToastActivity().javaClass)
        }

        button6.setOnClickListener {
            startActivity(SpeechMainActivity().javaClass)
        }

        button7.setOnClickListener {
            startActivity(MainActivity2().javaClass)
        }
    }
}
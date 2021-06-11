package com.xiayule.xiayucommom

import com.xiayule.commonlibrary.base.BaseActivity

class BlankActivity : BaseActivity() {

    override fun initContentView(): Int {
        return R.layout.blank_activity
    }

    override fun initView() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, BlankFragment.newInstance())
                .commitNow()
    }
}
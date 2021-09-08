package com.xiayule.xiayucommom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.xiayule.commonlibrary.base.BaseFragment

class BlankFragment : BaseFragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }

    private lateinit var blackFragmentViewModel: BlankFragmentViewModel

    override fun initContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_blank
    }

    override fun initViewModel() {
        blackFragmentViewModel = ViewModelProvider(this).get(BlankFragmentViewModel::class.java)
    }
}
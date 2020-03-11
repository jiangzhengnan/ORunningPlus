package com.oplayer.orunningplus.function.main.clockface


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oplayer.orunningplus.base.BaseFragment

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.today.mvp.ClockFaceContract

/**
 * A simple [Fragment] subclass.
 */
class ClockFaceFragment : BaseFragment(),ClockFaceContract.View {
    override fun onGetEvent(event: MessageEvent) {
       


    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_clock_face


    }

    override fun initInjector() {
       
    }

    override fun initView() {
       
    }

    override fun lazyLoadData() {
       
    }

    override fun testMessage(message: String) {
    }

    override fun hideLoading() {
       
    }

    override fun onError(message: String) {
       
    }


}

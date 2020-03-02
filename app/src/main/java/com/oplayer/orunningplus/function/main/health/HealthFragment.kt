package com.oplayer.orunningplus.function.main.health


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ng.lib_common.base.BaseFragment

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.health.mvp.HealthContract

/**
 * A simple [Fragment] subclass.
 */
class HealthFragment : BaseFragment(),HealthContract.View {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health, container, false)
    }

    override fun onGetEvent(event: MessageEvent) {

    }


    override fun getLayoutId(): Int {

        return R.layout.fragment_health

    }

    override fun initInjector() {

    }

    override fun initView() {

    }

    override fun lazyLoadData() {

    }

    override fun testMessage(message: String) {

    }

    override fun showAlert(message: String, enablePro: Boolean, iconResId: Int, showIcon: Boolean) {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {

    }


}

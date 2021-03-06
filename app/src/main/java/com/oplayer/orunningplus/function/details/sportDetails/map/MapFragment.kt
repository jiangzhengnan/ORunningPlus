package com.oplayer.orunningplus.function.details.sportDetails.map


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.oplayer.orunningplus.base.BaseFragment

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.service.BleService
import kotlinx.android.synthetic.main.fragment_map.*

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : BaseFragment() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_map
    }

    override fun initInjector() {
        
    }

    override fun onResume() {
        super.onResume()
        if (sport_user_image != null) {
            var path = BleService.INSTANCE.getCurrUser().iconPath
            var name = BleService.INSTANCE.getCurrUser().name
            if (path != null) Glide.with(this).load(path).into(sport_user_image)
            if (name != null) tv_user_name.text = name
        }
    }

    override fun initView() {




    }

    override fun lazyLoadData() {
        
    }

    override fun onGetEvent(event: MessageEvent) {
        
    }

    override fun hideLoading() {
        
    }

    override fun onError(message: String) {
        
    }


}

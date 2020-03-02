package com.oplayer.orunningplus.function.details.sleepDetails.month


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ng.lib_common.base.BaseFragment
import com.oplayer.common.utils.UIUtils

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.view.LinChart.LinChartLayout
import com.oplayer.orunningplus.view.LinChart.LinChartView
import com.oplayer.orunningplus.view.LinChart.SleepData
import com.oplayer.orunningplus.view.LinChart.SleepLineChartData
import kotlinx.android.synthetic.main.fragment_sleep_deatail_month.*

/**
 * A simple [Fragment] subclass.
 */
class SleepDetailMonthFragment : BaseFragment() {
    override fun onGetEvent(event: MessageEvent) {
        
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_sleep_deatail_month
    }

    override fun initInjector() {
        
    }

    override fun initView() {
        initLineChart()
    }

    override fun lazyLoadData() {
        
    }
    private fun initLineChart() {
//         var sleeps=mutableListOf<SleepData>()
//        sleeps.add(SleepData(LinChartView.SLEEP_TYPE_AWAKE,180))
//        sleeps.add(SleepData(LinChartView.SLEEP_TYPE_LIGHT,180))
//        sleeps.add(SleepData(LinChartView.SLEEP_TYPE_DEEP,180))
        var list= mutableListOf<SleepLineChartData>()
        for(index in 1..30){
            list.add(SleepLineChartData(null,0f,null))
        }
        linChartLayout_sleep_details.setData(list, UIUtils.getAndroiodScreenProperty(), LinChartLayout.TIME_TYPE_MONTH)
        linChartLayout_sleep_details.setView()
    }
    override fun hideLoading() {
        
    }

    override fun onError(message: String) {
        
    }


}

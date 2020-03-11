package com.oplayer.orunningplus.function.details.sleepDetails.week


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oplayer.orunningplus.base.BaseFragment
import com.oplayer.common.utils.UIUtils

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.view.LinChart.LinChartLayout
import com.oplayer.orunningplus.view.LinChart.LinChartView
import com.oplayer.orunningplus.view.LinChart.SleepData
import com.oplayer.orunningplus.view.LinChart.SleepLineChartData
import kotlinx.android.synthetic.main.fragment_sleep_deatail_week.*

/**
 * A simple [Fragment] subclass.
 */
class SleepDetailWeekFragment : BaseFragment() {
    override fun onGetEvent(event: MessageEvent) {
        
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_sleep_deatail_week
    }

    override fun initInjector() {
        
    }

    override fun initView() {

        initLineChart()

    }

    private fun initLineChart() {

        var list= mutableListOf<SleepLineChartData>()
        var sleeps=mutableListOf<SleepData>()
        sleeps.add(SleepData(LinChartView.SLEEP_TYPE_AWAKE,180))
        sleeps.add(SleepData(LinChartView.SLEEP_TYPE_LIGHT,180))
        sleeps.add(SleepData(LinChartView.SLEEP_TYPE_DEEP,180))
        for(index in 1..7){
            list.add(SleepLineChartData(null,0f,null))
        }
        linChartLayout_sleep_details.setData(list, UIUtils.getAndroiodScreenProperty(), LinChartLayout.TIME_TYPE_WEEK)
        linChartLayout_sleep_details.setView()
    }

    override fun lazyLoadData() {
        
    }

    override fun hideLoading() {
        
    }

    override fun onError(message: String) {
        
    }


}

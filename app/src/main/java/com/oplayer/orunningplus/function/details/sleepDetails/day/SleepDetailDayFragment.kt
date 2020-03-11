package com.oplayer.orunningplus.function.details.sleepDetails.day


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.oplayer.orunningplus.base.BaseFragment

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent

/**
 * A simple [Fragment] subclass.
 */
class SleepDetailDayFragment : BaseFragment(), OnChartValueSelectedListener {
    override fun getLayoutId(): Int {
        return R.layout.fragment_sleep_deatail_day
    }


    override fun initInjector() {
      
    }

    override fun initView() {

    }

    override fun lazyLoadData() {
      
    }

    override fun hideLoading() {
      
    }

    override fun onError(message: String) {
      
    }


    override fun onGetEvent(event: MessageEvent) {

    }

    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

}

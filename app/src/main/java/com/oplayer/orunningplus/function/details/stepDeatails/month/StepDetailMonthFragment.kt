package com.oplayer.orunningplus.function.details.stepDeatails.month


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
import com.oplayer.common.utils.UIUtils

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.view.DateSelectView
import kotlinx.android.synthetic.main.fragment_step_deatail_day.*
import kotlinx.android.synthetic.main.fragment_step_deatail_month.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class StepDetailMonthFragment : BaseFragment(), OnChartValueSelectedListener {
    override fun onGetEvent(event: MessageEvent) {
        
    }

    override fun getLayoutId(): Int {
     return  R.layout.fragment_step_deatail_month
        
    }

    override fun initInjector() {
        
    }

    override fun initView() {

        dsv_month.setListener(object : DateSelectView.onDateChageListener{
            override fun OnChage(date: Date) {
                showToast("时间改变  $date")
            }
        })



        initBarChartSetting(bc_step_month_detail)
        setData(bc_step_month_detail,getChartDate(), UIUtils.getColor(R.color.yellow_progress_color))
        initBarChartSetting(bc_distance_month_detail)
        setData(bc_distance_month_detail,getChartDate(), UIUtils.getColor(R.color.green_heart_color))
        initBarChartSetting(bc_calories_month_detail)
        setData(bc_calories_month_detail,getChartDate(), UIUtils.getColor(R.color.red_heart_color))

    }
    private fun initBarChartSetting(barChart: BarChart) {
        barChart.setOnChartValueSelectedListener(this)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.getDescription().setEnabled(false)
        barChart.setMaxVisibleValueCount(24)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)
        val xAxis: XAxis = barChart.getXAxis()
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelCount = 7
        xAxis.axisMaximum = 25f
        xAxis.axisMinimum = 0f
        xAxis.valueFormatter = xAxis.valueFormatter  //?
        val leftAxis: YAxis = barChart.getAxisLeft()
        leftAxis.setLabelCount(10, false)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 10f
        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 12000f
        barChart.axisRight.isEnabled=false
        barChart.legend.isEnabled=false

    }

    fun setData(mBarChart: BarChart, yVals1: List<BarEntry>, lineColorInt: Int) {
        var start = 1f
        var set: BarDataSet

        if (mBarChart.data != null && mBarChart.data.dataSetCount > 0) {
            set = mBarChart.getData().getDataSetByIndex(0) as BarDataSet
            set.setValues(yVals1)
            mBarChart.getData().notifyDataChanged()
            mBarChart.notifyDataSetChanged()
        } else {

            set = BarDataSet(yVals1, "")

            //设置有四种颜色
            set.setColors(lineColorInt)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.barWidth = 0.5f
            //设置数据
            mBarChart.data = data

        }
        //显示顶点值
        for (set in mBarChart.data.dataSets) {
            set.setDrawValues(!set.isDrawValuesEnabled)
        }
        mBarChart.animateXY(1000, 1000)
        mBarChart.invalidate()
    }
    private fun getChartDate(): List<BarEntry> {
        //模拟数据
        var dataSet = mutableListOf<BarEntry>()
        for (index in 1..24) {
            dataSet.add(BarEntry(index.toFloat(), (index * Math.random()*500).toFloat()))
        }
        return dataSet

    }
    override fun lazyLoadData() {
        
    }

    override fun hideLoading() {
        
    }

    override fun onError(message: String) {
        
    }

    override fun onNothingSelected() {
        
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        showToast("该项数值为: "+e.toString())

    }


}

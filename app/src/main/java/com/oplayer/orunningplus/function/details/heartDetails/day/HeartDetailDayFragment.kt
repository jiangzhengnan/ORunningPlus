package com.oplayer.orunningplus.function.details.heartDetails.day


import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.ng.lib_common.base.BaseFragment

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import kotlinx.android.synthetic.main.fragment_heart_details_day.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class HeartDetailDayFragment : BaseFragment() {
    override fun onGetEvent(event: MessageEvent) {
        
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_heart_details_day
    }

    override fun initInjector() {
        
    }
    private fun setData() {

        //1.设置x轴和y轴的点
        val entries: MutableList<Entry> =
            ArrayList()

        for (i in 0..10) entries.add(
            Entry(
                (i).toFloat(),
                Random().nextInt(150).toFloat()
            )
        )

        val dataSet = LineDataSet(entries, "") // add entries to dataset
        dataSet.color = Color.parseColor("#ff5500") //线条颜色
        dataSet.setCircleColor(Color.parseColor("#ff5500")) //圆点颜色
        dataSet.lineWidth = 1f //线条宽度
        val xAxis: XAxis = cc_heart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM //设置x轴的显示位置
        cc_heart.axisRight.isEnabled=false
        cc_heart.legend.isEnabled=false
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(false)
        cc_heart.description.isEnabled = false

        val leftAxis: YAxis = cc_heart.axisLeft
        leftAxis.setLabelCount(10, false)
//      leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setDrawAxisLine(false)
        leftAxis.enableGridDashedLine(5f, 5f, 0f);
        leftAxis.axisMinimum = 0f
        leftAxis.axisMaximum = 200f



        //3.chart设置数据
        val lineData = LineData(dataSet)
        cc_heart.data = lineData
        cc_heart.invalidate() // refresh
        cc_heart.animateY(2000) //动画效果，MPAndroidChart中还有很多动画效果可以挖掘
    }

    override fun initView() {
        setData()
    }

    override fun lazyLoadData() {
        
    }

    override fun hideLoading() {
        
    }

    override fun onError(message: String) {
        
    }


}

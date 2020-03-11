package com.oplayer.orunningplus.function.test

import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import kotlinx.android.synthetic.main.activity_test2.*
import java.util.*
import kotlin.collections.ArrayList


class Test2Activity : BaseActivity(), OnChartValueSelectedListener {


    override fun getLayoutId(): Int {
        return R.layout.activity_test2
    }

    override fun initData() {

    }

    override fun initView() {

                    pbProgress.setPercent(1F,"1\t\t06'13''")





    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {

    }

    override fun onGetEvent(event: MessageEvent) {

    }

    override fun onNothingSelected() {

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }
}


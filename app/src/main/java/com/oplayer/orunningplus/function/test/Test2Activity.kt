package com.oplayer.orunningplus.function.test

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.kct.bluetooth.pkt.FunDo.v
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.welcome.WelcomeActivity
import kotlinx.android.synthetic.main.activity_test2.*


class Test2Activity : BaseActivity(), OnChartValueSelectedListener {


    override fun getLayoutId(): Int {
        return R.layout.activity_test2
    }

    override fun initData() {

    }

    override fun initView() {

//                    pbProgress.setPercent(1F,"1\t\t06'13''")

startTo(WelcomeActivity::class.java)

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


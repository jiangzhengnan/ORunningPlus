package com.oplayer.orunningplus.function.test

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.kct.bluetooth.pkt.FunDo.v
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.welcome.WelcomeActivity
import com.oplayer.orunningplus.utils.DateUtil
import com.oplayer.orunningplus.view.DiscreteScroll.DiscreteScrollView
import kotlinx.android.synthetic.main.activity_test2.*
import java.util.*


class Test2Activity : BaseActivity(), OnChartValueSelectedListener {


    override fun getLayoutId(): Int {
        return R.layout.activity_test2
    }

    override fun initData() {

    }

    override fun initView() {

        var dateList = getDateList()
        var dateSelectAdapter = DateSelectAdapter(R.layout.item_layout_date, dateList)
        dsv_sport_date.adapter = dateSelectAdapter
        dsv_sport_date.setOffscreenItems(4) //Can also be set using android:overScrollMode xml attribute
        dsv_sport_date.setOverScrollEnabled(false) //Can also be set using android:overScrollMode xml attribute
        dsv_sport_date.scrollToPosition(11) //position becomes selected
        dsv_sport_date.addOnItemChangedListener { viewHolder, adapterPosition ->

            showToast("选择改变   $adapterPosition")
            viewHolder!!.itemView.findViewById<View>(R.id.view_line).visibility = View.VISIBLE
        }

        dsv_sport_date.addScrollListener { scrollPosition, currentPosition, newPosition, currentHolder, newCurrent ->
            var line = currentHolder!!.itemView.findViewById<View>(R.id.view_line)
            Slog.d("ScrollListener  scrollPosition  $scrollPosition  currentPosition $currentPosition   newPosition $newPosition ")
            line.visibility = View.GONE
        }


    }

    private fun getDateList(): List<Date> {
//reversed
        var dateList = mutableListOf<Date>()

        var date = Date()

        for (index in 1..10) {
            date = DateUtil.getBeforeMonth(date)!!
            dateList.add(date)
        }
        //倒序一下
        dateList.reverse()
        for (index in 1..10) {
            date = DateUtil.getNextMonth(date)!!
            dateList.add(date)
        }



        return dateList

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


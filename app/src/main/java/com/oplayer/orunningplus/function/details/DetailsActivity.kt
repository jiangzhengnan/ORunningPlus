package com.oplayer.orunningplus.function.details

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gigamole.navigationtabstrip.NavigationTabStrip
import com.oplayer.common.common.TodayDateType
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.details.heartDetails.day.HeartDetailDayFragment
import com.oplayer.orunningplus.function.details.heartDetails.month.HeartDetailMonthFragment
import com.oplayer.orunningplus.function.details.heartDetails.week.HeartDetailWeekFragment
import com.oplayer.orunningplus.function.details.sleepDetails.day.SleepDetailDayFragment
import com.oplayer.orunningplus.function.details.sleepDetails.month.SleepDetailMonthFragment
import com.oplayer.orunningplus.function.details.sleepDetails.week.SleepDetailWeekFragment
import com.oplayer.orunningplus.function.details.stepDeatails.day.StepDetailDayFragment
import com.oplayer.orunningplus.function.details.stepDeatails.month.StepDetailMonthFragment
import com.oplayer.orunningplus.function.details.stepDeatails.week.StepDetailWeekFragment
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_details
    }

    override fun initData() {

    }

    override fun initView() {

        initTopBar()

       val type= intent.getIntExtra(TodayDateType.TYPE,TodayDateType.STEP)

        initViewPager(type)


    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.iv_back -> {
                finish()
            }
            else -> {
            }
        }

    }

    override fun onGetEvent(event: MessageEvent) {

    }


    var fragmentList = mutableListOf<Fragment>()

    private fun initViewPager(type:Int) {

        when (type) {
            TodayDateType.STEP -> {
                //初始化计步
                fragmentList.add(StepDetailDayFragment())
                fragmentList.add(StepDetailWeekFragment())
                fragmentList.add(StepDetailMonthFragment())
            }
            TodayDateType.HEART -> {
                //初始化心率
                fragmentList.add(HeartDetailDayFragment())
                fragmentList.add(HeartDetailWeekFragment())
                fragmentList.add(HeartDetailMonthFragment())
            }
            TodayDateType.SLEEP -> {
                //初始化睡眠
                fragmentList.add(SleepDetailDayFragment())
                fragmentList.add(SleepDetailWeekFragment())
                fragmentList.add(SleepDetailMonthFragment())


            }
            TodayDateType.SPORT -> { }
        }







        nvp_detail.adapter = ViewPagerAdapter(supportFragmentManager)
        nvp_detail.offscreenPageLimit = 3
    }

    private fun initTopBar() {
        nts_date.setTabIndex(0, true)
        nts_date.onTabStripSelectedIndexListener =
            object : NavigationTabStrip.OnTabStripSelectedIndexListener {
                override fun onEndTabSelected(title: String?, index: Int) {

                    Slog.d("页面切换 title $title  Int $index ")
                    nvp_detail.setCurrentItem(index, false)
                }

                override fun onStartTabSelected(title: String?, index: Int) {
                }

            }

    }

    inner class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

        var fm: FragmentManager? = null

        init {
            this.fm = fragmentManager
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList.get(position)
        }

        override fun getCount(): Int {
            return fragmentList.size
        }
    }


}

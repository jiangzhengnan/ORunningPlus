package com.oplayer.orunningplus.function.details.sportDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gigamole.navigationtabstrip.NavigationTabStrip
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.details.sportDetails.chart.ChartFragment
import com.oplayer.orunningplus.function.details.sportDetails.map.MapFragment
import com.oplayer.orunningplus.function.details.sportDetails.overview.OverViewFragment
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_sport_details.*
import kotlinx.android.synthetic.main.activity_sport_details.nts_date

class SportDetailsActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_sport_details
    }

    override fun initData() {
        
    }

    override fun initView() {


        tv_sport_type.setText("Running")

        initTopBar()

        initViewPager()

    }
    var fragmentList = mutableListOf<Fragment>()

    private fun initViewPager() {
        fragmentList.add(MapFragment())
        fragmentList.add(OverViewFragment())
        fragmentList.add(ChartFragment())

        nvp_sport_detail.adapter = ViewPagerAdapter(supportFragmentManager)
        nvp_sport_detail.offscreenPageLimit = 3
    }

    private fun initTopBar() {
        nts_date.setTabIndex(0, true)
        nts_date.onTabStripSelectedIndexListener =
            object : NavigationTabStrip.OnTabStripSelectedIndexListener {
                override fun onEndTabSelected(title: String?, index: Int) {

                    Slog.d("页面切换 title $title  Int $index ")
                    nvp_sport_detail.setCurrentItem(index, false)
                }

                override fun onStartTabSelected(title: String?, index: Int) {
                }

            }

    }






    override fun initInfo() {
        
    }

    override fun onClick(v: View) {
        
    }

    override fun onGetEvent(event: MessageEvent) {
        
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

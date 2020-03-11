package com.oplayer.orunningplus.function.sportStatistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigamole.navigationtabstrip.NavigationTabStrip
import com.oplayer.common.common.SportModel
import com.oplayer.common.common.TodayDateType
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.SportModelItem
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.sport.SportModeSelectAdapter
import com.oplayer.orunningplus.function.sportStatistics.all.AllStatisFragment
import com.oplayer.orunningplus.function.sportStatistics.month.MonthStatisFragment
import com.oplayer.orunningplus.function.sportStatistics.week.WeekStatisFragment
import com.oplayer.orunningplus.function.sportStatistics.year.YearStatisFragment
import kotlinx.android.synthetic.main.activity_sport_statistics.*
import kotlinx.android.synthetic.main.activity_sport_statistics.tv_sport_mode

class SportStatisticsActivity : BaseActivity(), SportStatisticsContract.View, View.OnClickListener {


    override fun getLayoutId(): Int {
        return R.layout.activity_sport_statistics;
    }

    override fun initData() {

    }

    override fun initView() {

        initTopBar()

        val type= intent.getIntExtra(TodayDateType.TYPE, TodayDateType.STEP)

        initViewPager()




        tv_sport_mode.setOnClickListener(this)
        iv_open.setOnClickListener(this)
        iv_share.setOnClickListener(this)
    }
    var fragmentList = mutableListOf<Fragment>()

    private fun initViewPager() {

        fragmentList.add(WeekStatisFragment())
        fragmentList.add(MonthStatisFragment())
        fragmentList.add(YearStatisFragment())
        fragmentList.add(AllStatisFragment())
        nvp_detail.adapter = ViewPagerAdapter(supportFragmentManager)
        nvp_detail.offscreenPageLimit = 4
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

    override fun initInfo() {

    }

    override fun onClick(v: View) {


        when (v.id) {
            R.id.iv_share -> {
                UIUtils.originalShareImage(this,this)

            }
            else -> {
                showPopupWindow(rv_toolbar)
            }
        }


    }

    override fun onGetEvent(event: MessageEvent) {

    }

    private var popupWindow = PopupWindow(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )

    fun showPopupWindow(view: View) {


        val popupView = LayoutInflater.from(this).inflate(R.layout.pop_sport_model, null)

        var recyclerView = popupView.findViewById<RecyclerView>(R.id.rv_sport_model)

        initPopRV(recyclerView)

        popupWindow.contentView = popupView

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
//        popupWindow.animationStyle = R.style.RtcPopupAnimation
        popupWindow.isClippingEnabled = false


        if (!popupWindow.isShowing) {
            popupWindow.showAsDropDown(view)
        }

    }


    var selectModelPosion = SportModel.MODE_CROSS_RUN

    private fun initPopRV(recyclerView: RecyclerView?) {

        val modes = initSportModel(selectModelPosion)
        var sportModelAdapter = SportModeSelectAdapter(R.layout.item_sport_model, modes)
        recyclerView!!.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = sportModelAdapter

        sportModelAdapter.setOnItemClickListener { adapter, view, position ->
            var sportModelItem = adapter.data[position] as SportModelItem
            selectModelPosion = sportModelItem.ModelType
            Slog.d("当前选中  position $position   sportModelItem $sportModelItem ")
            tv_sport_mode.text = sportModelItem.ModelTypeStr
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }

        }

    }

    private fun initSportModel(selectModel: Int): List<SportModelItem> {
        var models = mutableListOf<SportModelItem>()

        models.add(
            SportModelItem(
                selectModel == SportModel.MODE_CROSS_RUN,
                R.mipmap.sport_type_running,
                R.mipmap.sport_type_running_gray,
                getString(R.string.sport_type_run),
                SportModel.MODE_CROSS_RUN
            )
        )
        models.add(
            SportModelItem(
                selectModel == SportModel.MODE_WALKING,
                R.mipmap.sport_type_walking,
                R.mipmap.sport_type_walking_gray,
                getString(R.string.sport_type_walk),
                SportModel.MODE_WALKING
            )
        )
        models.add(
            SportModelItem(
                selectModel == SportModel.MODE_RUN_INSIDE,
                R.mipmap.sport_type_runindoor,
                R.mipmap.sport_type_runindoor_gray,
                getString(R.string.sport_type_runindoor),
                SportModel.MODE_RUN_INSIDE
            )
        )
        models.add(
            SportModelItem(
                selectModel == SportModel.MODE_CYCLING,
                R.mipmap.sport_type_cycling,
                R.mipmap.sport_type_cycling_gray,
                getString(R.string.sport_type_cycing),
                SportModel.MODE_CYCLING
            )
        )
        models.add(
            SportModelItem(
                selectModel == SportModel.MODE_SWIMMING,
                R.mipmap.sport_type_swimming,
                R.mipmap.sport_type_swimming_gray,
                getString(R.string.sport_type_swim)
                ,
                SportModel.MODE_SWIMMING
            )
        )
        models.add(
            SportModelItem(
                selectModel == SportModel.MODE_HIKING,
                R.mipmap.sport_type_hiking,
                R.mipmap.sport_type_hiking_gray,
                getString(R.string.sport_type_hiking),
                SportModel.MODE_HIKING
            )
        )

        return models
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

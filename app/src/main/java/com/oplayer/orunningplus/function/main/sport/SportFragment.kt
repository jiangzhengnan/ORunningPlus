package com.oplayer.orunningplus.function.main.today


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oplayer.orunningplus.base.BaseFragment
import com.oplayer.common.common.SportModel
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.Sport
import com.oplayer.orunningplus.bean.SportModelItem
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.details.sportDetails.SportDetailsActivity
import com.oplayer.orunningplus.function.main.sport.SportAdapter
import com.oplayer.orunningplus.function.main.sport.SportModeSelectAdapter
import com.oplayer.orunningplus.function.main.today.mvp.SportContract
import com.oplayer.orunningplus.function.main.today.mvp.SportPresenter
import com.oplayer.orunningplus.function.sportStatistics.SportStatisticsActivity
import com.oplayer.orunningplus.function.test.DateSelectAdapter
import com.oplayer.orunningplus.utils.DateUtil
import kotlinx.android.synthetic.main.fragment_sport.*
import kotlinx.android.synthetic.main.fragment_sport.dsv_sport_date
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SportFragment : BaseFragment(), SportContract.View, View.OnClickListener {


    lateinit var mPresenter: SportContract.Presenter


    override fun testMessage(message: String) {


    }

    override fun onGetEvent(event: MessageEvent) {


    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_sport

    }

    override fun initInjector() {
        mPresenter = SportPresenter()
        mPresenter.attachView(this)
    }

    override fun initView() {
        tv_sport_mode.setOnClickListener(this)
        iv_open.setOnClickListener(this)
        iv_statistics.setOnClickListener(this)

        initRV()

        initDSV()

    }

    private fun initRV() {

        var list = mutableListOf<Sport>()


        //模拟数据

        for(index in 1..10){

            list.add(Sport())
        }



        rv_sport.layoutManager = LinearLayoutManager(activity)
        var sportAdapter = SportAdapter(R.layout.item_sport, list)
        sportAdapter.setOnItemClickListener {    adapter, view, position ->
            startTo(SportDetailsActivity::class.java)

        }

        rv_sport.adapter = sportAdapter


    }

    private fun initDSV() {
        var dateList = getDateList()
        var dateSelectAdapter = DateSelectAdapter(R.layout.item_layout_date, dateList)
        dsv_sport_date.adapter = dateSelectAdapter
        dsv_sport_date.setOffscreenItems(4) //Can also be set using android:overScrollMode xml attribute
        dsv_sport_date.setOverScrollEnabled(false) //Can also be set using android:overScrollMode xml attribute
        dsv_sport_date.scrollToPosition(19) //position becomes selected
        dsv_sport_date.addOnItemChangedListener { viewHolder, adapterPosition ->

            showToast("选择改变   $adapterPosition")
            viewHolder!!.itemView.findViewById<View>(R.id.view_line).visibility = View.VISIBLE

            var monthArr = activity!!.resources.getStringArray(R.array.candler_month_arr)
            var date = dateSelectAdapter.data[adapterPosition]




            tv_date_str.setText("${monthArr[DateUtil.getMonth(date) - 1]} ${DateUtil.getYear(date)}")
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

        for (index in 1..20) {
            dateList.add(date)
            date = DateUtil.getBeforeMonth(date)!!

        }
        //倒序一下
        dateList.reverse()
        for (index in 1..3) {
            date = dateList.last()
            date = DateUtil.getNextMonth(date)!!
            dateList.add(date)
        }



        return dateList

    }


    override fun lazyLoadData() {

        mPresenter.getTestMessage()
    }


    override fun showAlert(message: String, enablePro: Boolean, iconResId: Int, showIcon: Boolean) {


    }

    override fun hideLoading() {


    }

    override fun onError(message: String) {


    }

    override fun onDetach() {
        super.onDetach()
        if (::mPresenter.isInitialized) {
            mPresenter.detachView()
        }


    }

    private var popupWindow = PopupWindow(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )

    fun showPopupWindow(view: View) {


        val popupView = LayoutInflater.from(context).inflate(R.layout.pop_sport_model, null)

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


    var selectModelPosion = SportModel.MODE_ALL

    private fun initPopRV(recyclerView: RecyclerView?) {


        val modes = initSportModel(selectModelPosion)
        var sportModelAdapter = SportModeSelectAdapter(R.layout.item_sport_model, modes)
        recyclerView!!.layoutManager = GridLayoutManager(activity, 4)
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
                selectModel == SportModel.MODE_ALL,
                R.mipmap.sport_type_all,
                R.mipmap.sport_type_all_gray,
                getString(R.string.sport_type_all),
                SportModel.MODE_ALL

            )
        )
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_statistics -> {
                startTo(SportStatisticsActivity::class.java)
            }
            else -> {
                showPopupWindow(rv_toolbar)
            }
        }

    }

}



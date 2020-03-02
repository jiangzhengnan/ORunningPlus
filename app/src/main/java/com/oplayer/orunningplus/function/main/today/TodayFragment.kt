package com.oplayer.orunningplus.function.main.today


import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.ng.lib_common.base.BaseFragment
import com.oplayer.common.common.BluetoothState
import com.oplayer.common.common.Constants
import com.oplayer.common.common.TodayDateType
import com.oplayer.common.utils.PreferencesHelper
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.calender.CalenderActivity
import com.oplayer.orunningplus.function.details.DetailsActivity
import com.oplayer.orunningplus.function.main.ManageActivity
import com.oplayer.orunningplus.function.main.today.mvp.TodayAdapter
import com.oplayer.orunningplus.function.main.today.mvp.TodayContract
import com.oplayer.orunningplus.function.main.today.mvp.TodayData
import com.oplayer.orunningplus.function.main.today.mvp.TodayPresenter
import com.oplayer.orunningplus.service.BleService
import com.oplayer.orunningplus.utils.DateUtil
import com.scwang.smartrefresh.layout.header.BezierRadarHeader
import kotlinx.android.synthetic.main.fragment_today.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class TodayFragment : BaseFragment(), TodayContract.View {


    lateinit var mPresenter: TodayContract.Presenter

    companion object {
        const val requestInt = 1
        const val resultInt = 2
        //界面传值key
        const val SELECT_DATE = "SELECT_DATE"
    }

    var currDate = Date()

    override fun onGetEvent(event: MessageEvent) {

        when (event.getMessageType()) {
            Constants.BLUETOOTH_MESSAGE -> {
                refreshBTState(event.getMessage() as String)
            }


            else -> {
                Slog.d("首页布局更新  未知消息")

            }
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_today

    }


    override fun initView() {
        initToolBar()
        if (BleService.INSTANCE.isConnected()) {
            refreshBTState(BluetoothState.CONNECTION_SUCCESS)
        } else {
            refreshBTState(BluetoothState.CONNECTION_FAILED)
        }
        setDate(currDate)
        initSRL()
        initDSV()
        initOnClick()

    }

    private fun initOnClick() {
        tv_day.setOnClickListener {
            val intent = Intent(activity, CalenderActivity::class.java)
            intent.putExtra(SELECT_DATE, DateUtil.getCurDateStr(currDate))
            startActivityForResult(intent, requestInt)
        }

        iv_share.setOnClickListener {
            showToast("点击分享按钮")
            UIUtils.originalShareImage(activity!!,context!!)

        }


    }

    private fun setDate(date: Date) {
        this.currDate = date
        tv_time.text = DateUtil.date2Str(date, "dd/MM/yyyy")
        tv_day.text = DateUtil.getDay(date).toString()

        Slog.d("时间改变 切换数据源")

    }

    private fun initSRL() {
        //设置 Header 为 贝塞尔雷达 样式
//        srl_main.setPrimaryColors(getBGGrayColor())
        srl_main.setPrimaryColorsId(R.color.colorPrimary)
        srl_main.setRefreshHeader(BezierRadarHeader(mActivity).setEnableHorizontalDrag(true))
        srl_main.setEnableLoadMore(false)//是否启用上拉加载功能
        srl_main.setOnRefreshListener {
            Slog.d("启用刷新方法  ")
            it.finishRefresh(2000/*,false*/)//传入false表示刷新失败
        }


    }

    private fun initToolBar() {


    }

    val todayList = mutableListOf<TodayData>()
    var mainAdapter = TodayAdapter(todayList)

    init {
        refreshCard()

    }

    override fun onResume() {
        super.onResume()
        todayList.clear()
        refreshCard()
        Slog.d("首页布局更新  ${todayList.toString()}")
        mainAdapter.notifyDataSetChanged()

    }


    //初始化首页显示状态
    fun refreshCard() {
        if (PreferencesHelper.isShowStep()) todayList.add(TodayData(TodayDateType.STEP))
        if (PreferencesHelper.isShowHr()) todayList.add(TodayData(TodayDateType.HEART))
        if (PreferencesHelper.isShowSleep()) todayList.add(TodayData(TodayDateType.SLEEP))
        if (PreferencesHelper.isShowSport()) todayList.add(TodayData(TodayDateType.SPORT))
        todayList.add(TodayData(TodayDateType.MANAGE))
    }

    private fun initDSV() {


        mainAdapter = TodayAdapter(todayList)
        crv_main.layoutManager = LinearLayoutManager(activity)
        mainAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        mainAdapter.setOnItemClickListener { adapter, view, position ->
            var itemType = adapter.getItemViewType(position)
            when (itemType) {

                TodayDateType.MANAGE -> {
                    Slog.d("多布局识别:   ${TodayDateType.MANAGE}")
                    startTo(ManageActivity::class.java)
                }
                else -> {
                    var intent = Intent(activity, DetailsActivity::class.java)
                    intent.putExtra(TodayDateType.TYPE, itemType)
                    startTo(intent)
                }

            }
        }
        crv_main.adapter = mainAdapter
    }

    override fun lazyLoadData() {

        initBTState()

    }


    override fun showAlert(message: String, enablePro: Boolean, iconResId: Int, showIcon: Boolean) {


    }

    override fun hideLoading() {


    }

    override fun onError(message: String) {


    }


    private fun refreshBTState(str: String) {

        when (str) {
            BluetoothState.CONNECTION_SUCCESS -> {
                Slog.d("连接成功  ${BleService.INSTANCE.getCurrDevice()} ")
                iv_conn.setImageResource(R.mipmap.today_connect)
                tv_conn.text = BleService.INSTANCE.getCurrDevice().bleName

            }
            BluetoothState.CONNECTION_FAILED -> {
                iv_conn.setImageResource(R.mipmap.today_disconnect); tv_conn.setText(
                    UIUtils.getString(
                        R.string.device_state_not_conn
                    )
                )
            }
            BluetoothState.CONNECTIONNTING -> {
                iv_conn.setImageResource(R.mipmap.today_disconnect); tv_conn.setText(
                    UIUtils.getString(
                        R.string.device_state_connectionning
                    )
                )
            }

        }

    }

    private fun initBTState() {

        var device = BleService.INSTANCE.getCurrDevice()
        if (device.isBind!!) {
            iv_conn.setImageResource(R.mipmap.today_connect); tv_conn.setText(BleService.INSTANCE.getCurrDevice().bleName)
        } else {
            iv_conn.setImageResource(R.mipmap.today_disconnect); tv_conn.setText(UIUtils.getString(R.string.device_state_not_conn))
        }

    }


    //建立引用
    override fun initInjector() {
        mPresenter = TodayPresenter()
        mPresenter.attachView(this)
    }


    //销毁引用 防止泄露
    override fun onDetach() {
        super.onDetach()
        mPresenter.detachView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Slog.d("回调到fragment")

        if (requestInt == requestCode && resultCode == resultInt) {
            val result: String? = data!!.getStringExtra(SELECT_DATE)
            val date = DateUtil.str2Date(result, "yyyy-MM-dd")

            if (date != null) {
                setDate(date)
            }

            Slog.d("接收到选择值 $result")
        }
    }

}

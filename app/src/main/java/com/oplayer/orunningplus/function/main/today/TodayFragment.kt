package com.oplayer.orunningplus.function.main.today


import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ng.lib_common.base.BaseFragment
import com.oplayer.common.common.BluetoothState
import com.oplayer.common.common.Constants
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.common.TodayDateType
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.today.mvp.TodayAdapter
import com.oplayer.orunningplus.function.main.today.mvp.TodayContract
import com.oplayer.orunningplus.function.main.today.mvp.TodayData
import com.oplayer.orunningplus.function.main.today.mvp.TodayPresenter
import com.oplayer.orunningplus.function.main.today.mvp.CandlerAdapter
import com.oplayer.orunningplus.function.profile.MyProfileActivity
import com.oplayer.orunningplus.service.BleService
import com.oplayer.orunningplus.utils.javautils.JavaUtil
import com.oplayer.orunningplus.view.DiscreteScroll.DSVOrientation
import com.oplayer.orunningplus.view.DiscreteScroll.InfiniteScrollAdapter
import com.oplayer.orunningplus.view.DiscreteScroll.SpaceItemDecoration
import com.oplayer.orunningplus.view.DiscreteScroll.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_today.*
import org.greenrobot.eventbus.EventBus
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class TodayFragment : BaseFragment(), TodayContract.View {


    lateinit var mPresenter: TodayContract.Presenter
    var mTodayPosition: Int = 0
    var mCurrDate: Date

    var dateList = mutableListOf<Date>()

    init {
        initDateList()
        mCurrDate = Date()
        mTodayPosition = dateList.size - 90
    }


    /**
     * 构建首页日历
     * */

    private fun initDateList(): List<Date> {
        var tmpTime: Long = System.currentTimeMillis()
        var leftTime: Long = System.currentTimeMillis()
        for (index in 1..90) {
            dateList.add(Date(tmpTime)); tmpTime += 1000 * 60 * 60 * 24
        }
        for (index in 0..90) {
            leftTime -= 1000 * 60 * 60 * 24; dateList.add(0, Date(leftTime))
        }
        return dateList
    }


    override fun onGetEvent(event: MessageEvent) {

        when (event.getMessageType()) {
            Constants.BLUETOOTH_MESSAGE -> {
                refreshBTState(event.getMessage() as String)
            }


            else -> {
            }
        }

    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.iv_refresh -> {

                EventBus.getDefault().post(MessageEvent(DeviceSetting, DeviceSetting.TODAY_QUERY))
            }
            else -> {
            }
        }


    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_today

    }


    override fun initView() {


        initToolBar()

        tv_conn.setText("unKnow")
        initCander()
        initDSV()

    }

    private fun initToolBar() {
        toolbar_title.text = UIUtils.getString(R.string.main_today)
        iv_profile.setOnClickListener {
            startTo(context, MyProfileActivity::class.java)

        }
    }


    private fun initCander() {
        val candlerAdapter =
            CandlerAdapter(
                R.layout.item_today_candler,
                dateList
            )
        dsv_candler.setOverScrollEnabled(true)
        dsv_candler.setSlideOnFling(false)
        dsv_candler.setOffscreenItems(7)
        dsv_candler.setOrientation(DSVOrientation.HORIZONTAL)
        dsv_candler.setOnScrollListener(mOnScrollListener)
        dsv_candler.adapter = candlerAdapter
        dsv_candler.scrollToPosition(mTodayPosition)
        view_today.setOnClickListener {
            //点击按钮滑动至当前选项
            dsv_candler.smoothScrollToPosition(mTodayPosition)
            view_today.visibility = View.GONE
            mCurrDate = Date()
        }


    }

    private fun initDSV() {

        val todayList = mutableListOf<TodayData>()
        todayList.add(TodayData(TodayDateType.STEP))
        todayList.add(TodayData(TodayDateType.HEART))
        todayList.add(TodayData(TodayDateType.SLEEP))
        todayList.add(TodayData(TodayDateType.SPORT))
        val mainAdapter = TodayAdapter(todayList)
        val wrapper: InfiniteScrollAdapter<*> =
            InfiniteScrollAdapter.wrap<RecyclerView.ViewHolder>(mainAdapter)
        crv_main.setOrientation(DSVOrientation.VERTICAL)
        crv_main.adapter = wrapper
        crv_main.setItemTransitionTimeMillis(100)
        crv_main.setOverScrollEnabled(true)
        crv_main.setSlideOnFling(true)
        crv_main.setOffscreenItems(4)
        //默认位置1
        crv_main.scrollToPosition(3)
        crv_main.addItemDecoration(SpaceItemDecoration(0, 1))
        crv_main.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )

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
        showToast(str)
        when (str) {
            BluetoothState.CONNECTION_SUCCESS -> {
                iv_conn.setImageResource(R.mipmap.today_connect); tv_conn.setText(BleService.INSTANCE.getCurrDevice().bleName)
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


    private val mOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            /**
             * 监听滑动时间 上色
             * */
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val childCount = recyclerView.childCount//总item的数量
                val width = recyclerView.getChildAt(0).width//第一个item的宽度
                val padding =
                    (recyclerView.width - width) / 2//这个padding是 recycler的宽度减去第一个item的宽度然后除以2，作为padding
                for (index in 0..childCount) {
                    val v = recyclerView.getChildAt(index) ?: return//获取每一个child
                    var rate = 0f//是一个缩放比例
                    if (v.left <= padding) {//如果view距离左边的宽度 小于等于 左侧剩余空间(padding) （意味着这个view开始往左边滑动了，并且有遮挡）
                        if (v.left >= padding - v.width) {//如果view距离左边的距离 小于等于滑进去的距离 （其实就是说滑动到一半的时候）
                            v.findViewById<TextView>(R.id.tv_month)
                                .setTextColor(UIUtils.getColor(R.color.icon_green_color))
                            v.findViewById<TextView>(R.id.tv_day)
                                .setTextColor(UIUtils.getColor(R.color.icon_green_color))
                            v.findViewById<TextView>(R.id.tv_week)
                                .setTextColor(UIUtils.getColor(R.color.icon_green_color))
                            rate =
                                (padding - v.left) * 1.1f / v.width//（这个比例的计算结果一般都会大于1，这样一来，根据下面的 1- rate * 0.1 得出，这个比例最多不会到达1，也就是 1- 0.1， 也就是 0.9， 所以这个view的宽度最大不会小于他本身的90%）
                        } else {
                            rate = 1f
                            v.findViewById<TextView>(R.id.tv_month)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                            v.findViewById<TextView>(R.id.tv_day)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                            v.findViewById<TextView>(R.id.tv_week)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                        }

                        v.scaleY = 1 - rate * 0.1f
                    } else {
                        if (v.left <= recyclerView.width - padding) {//这个过程大概是指这个view 从最后侧刚刚出现的时候开始滑动过padding的距离
                            rate =
                                (recyclerView.width - padding - v.left) * 1f / v.width
                            v.findViewById<TextView>(R.id.tv_month)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                            v.findViewById<TextView>(R.id.tv_day)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                            v.findViewById<TextView>(R.id.tv_week)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))

                        }
                        v.scaleY = 0.9f + rate * 0.1f
                    }


                }


            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {

                        Slog.d("滑动停止  ")

                        var currItem = dsv_candler.currentItem - 1

                        val diff = JavaUtil.getAbs(currItem - mTodayPosition)
                        //中点
                        if (diff > 2) {
                            view_today.visibility = View.VISIBLE
                            Slog.d("显示按钮")
                        } else {
                            view_today.visibility = View.GONE
                        }
                        mCurrDate = dateList[currItem]


                    }

                    RecyclerView.SCROLL_STATE_DRAGGING -> {
                        Slog.d("RecyclerView.SCROLL_STATE_DRAGGING")
                    }
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        Slog.d("RecyclerView.SCROLL_STATE_SETTLING")
                    }

                }


            }

        }





}

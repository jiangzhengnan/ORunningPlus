package com.oplayer.orunningplus.function.main.today


import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ng.lib_common.base.BaseFragment
import com.oplayer.common.common.BluetoothState
import com.oplayer.common.common.Constants
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.common.TodayDateType
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.today.mvp.TodayAdapter
import com.oplayer.orunningplus.function.main.today.mvp.TodayContract
import com.oplayer.orunningplus.function.main.today.mvp.TodayData
import com.oplayer.orunningplus.function.main.today.mvp.TodayPresenter
import com.oplayer.orunningplus.service.BleService
import com.oplayer.orunningplus.view.DiscreteScroll.DSVOrientation
import com.oplayer.orunningplus.view.DiscreteScroll.InfiniteScrollAdapter
import com.oplayer.orunningplus.view.DiscreteScroll.SpaceItemDecoration
import com.oplayer.orunningplus.view.DiscreteScroll.transform.ScaleTransformer
import kotlinx.android.synthetic.main.fragment_today.*
import org.greenrobot.eventbus.EventBus


/**
 * A simple [Fragment] subclass.
 */
class TodayFragment : BaseFragment(), TodayContract.View {


    lateinit var mPresenter: TodayContract.Presenter


    override fun testMessage(message: String) {


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

    override fun initInjector() {
        mPresenter = TodayPresenter()
        mPresenter.attachView(this)
    }

    override fun initView() {
        toolbar_title.text = UIUtils.getString(R.string.main_today)

        tv_conn.setText("unKnow")
        initDSV()
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
        crv_main.setItemTransitionTimeMillis(300)
        crv_main.setOverScrollEnabled(true)
        crv_main.setSlideOnFling(true)
        crv_main.setOffscreenItems(4)
        //默认位置1
        crv_main.scrollToPosition(1)
        crv_main.addItemDecoration(SpaceItemDecoration(0, 1))
        crv_main.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )

    }

    override fun lazyLoadData() {

        initBTState()
        mPresenter.getTestMessage()
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


    override fun onDetach() {
        super.onDetach()
        mPresenter.detachView()
    }

}

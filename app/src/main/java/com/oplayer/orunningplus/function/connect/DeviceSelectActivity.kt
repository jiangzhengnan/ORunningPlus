package com.oplayer.orunningplus.function.connect

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.oplayer.common.common.Constants
import com.oplayer.common.common.DeviceType
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.connect.mvp.*
import kotlinx.android.synthetic.main.activity_device_select.*

class DeviceSelectActivity : BaseActivity(), DeviceSelectContract.View {


    lateinit var mPresenter: DeviceSelectContract.Presenter

    override fun getLayoutId(): Int {
        return R.layout.activity_device_select
    }

    override fun initData() {
        mPresenter = DeviceSelectPresenter()
        mPresenter.attachView(this)
        mPresenter.getDeviceData()
    }

    private lateinit var mDeviceAdapter: DeviceSelectAdapter


    override fun initView() {
        rv_devices.layoutManager = LinearLayoutManager(this)
        initToolbar(UIUtils.getString(R.string.settings_conn), true)
    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {

    }

    override fun onGetEvent(event: MessageEvent) {

        when (event.getMessageType()) {
            Constants.DEVICE_SELECT_ONCLICK -> {

                var device = event.getMessage() as DeviceDetailData

                when (device.Type) {
                    DeviceType.DEVICE_FUNDO -> {
                        showToast("选择设备为  ${device.Type}")
                        startTo(ConnectActivity::class.java)
                    }
                    else -> {
                    }
                }

            }
            else -> {
            }
        }

    }


    override fun showDeviceDatas(list: List<MultiItemEntity>) {

        mDeviceAdapter = DeviceSelectAdapter(list)
        rv_devices.adapter = mDeviceAdapter

    }

    override fun onDestroy() {
        super.onDestroy()

        mPresenter.detachView()
    }

}

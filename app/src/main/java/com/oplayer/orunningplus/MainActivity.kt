package com.oplayer.orunningplus

import android.view.View
import com.oplayer.common.common.CompanyCode
import com.oplayer.common.common.PermissList
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.DeviceInfo
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.connect.ConnectActivity
import com.oplayer.orunningplus.service.BleService
import com.vicpin.krealmextensions.createOrUpdate
import com.vicpin.krealmextensions.queryAll
import com.vicpin.krealmextensions.queryFirst
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initData() {
    }

    override fun initView() {
        testMethod()
    }


    override fun onResume() {
        super.onResume()


        if (tv_start != null) {
            var device = DeviceInfo().queryFirst()
            if (device != null) {
                tv_start.setText(device.toString())
            }

        }


    }

    override fun initInfo() {
        checkBTState()
        checkPermission(PermissList.PERMISSIONS_LIST)
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.tv_start -> {
                startTo(ConnectActivity::class.java)
            }

        }

    }

    override fun onGetEvent(event: MessageEvent) {
    }


    private fun testMethod() {
        Slog.d("currdevice  ${BleService.INSTANCE.getCurrDevice()}")
        BleService.INSTANCE.setBind(true)
        Slog.d("changedevice  ${BleService.INSTANCE.getCurrDevice()}")
    }
}

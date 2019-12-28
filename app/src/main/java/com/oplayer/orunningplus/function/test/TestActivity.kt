package com.oplayer.orunningplus.function.test

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.common.SystemSetting
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.DeviceInfo
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.connect.ConnectActivity
import com.oplayer.orunningplus.service.BleService
import com.vicpin.krealmextensions.queryAsFlowable
import com.vicpin.krealmextensions.queryFirst
import kotlinx.android.synthetic.main.activity_test.*
import org.greenrobot.eventbus.EventBus
import java.lang.StringBuilder

class TestActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_test

    var message = StringBuilder()

    @SuppressLint("CheckResult")
    override fun initData() {

        DeviceInfo().queryAsFlowable { queryFirst<DeviceInfo>() }.subscribe {
            Slog.d(" 数据变化    ")
            var deviceInfo = it.first()
            message.append("bleName:   ${deviceInfo.bleName}  \n")
            message.append("bleAddress:${deviceInfo.bleAddress}  \n")
            message.append("battery:   ${deviceInfo.battery}  \n")

            tv_message.text = message.toString()
            message = StringBuilder()
        }


    }

    override fun initView() {

        val tvMessage = findViewById<TextView>(R.id.tv_message)

    }

    override fun initInfo() {
        checkNotification()
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btn_conn -> startTo(ConnectActivity::class.java)
            R.id.btn_disconn -> BleService.INSTANCE.disConnBle()
            R.id.btn_find_device -> {
                EventBus.getDefault().post(MessageEvent(DeviceSetting, DeviceSetting.FIND_DEVICE))
            }
            R.id.btn_query_power -> {
                EventBus.getDefault().post(MessageEvent(DeviceSetting, DeviceSetting.QUERY_BATTERY))
            }

        }

    }

    override fun onGetEvent(event: MessageEvent) {

    }


    fun checkNotification() {
        /**
         * Fadein, Slideleft, Slidetop, SlideBottom,
         * Slideright, Fall, Newspager, Fliph, Flipv,
         * RotateBottom, RotateLeft, Slit, Shake, Sidefill
         * */

        if(isNotificationEnabled()){
            showToast("通知使用权已经开启  ")
          return
        }
        val dialogBuilder=  NiftyDialogBuilder.getInstance(this)

        dialogBuilder .withTitle("通知使用权")
            .withMessage("请开启通知使用权")
            .withEffect(Effectstype.RotateBottom)
            .withButton1Text("取消")
            .withButton2Text("确定")
            .isCancelableOnTouchOutside(true)
            .setButton1Click {
                showToast("点击按钮1")
                dialogBuilder.dismiss()
            }
            .setButton2Click {
                showToast("点击按钮2")
                startActivity(SystemSetting.NOTIFICATION_LISTENER_INTENT)
                dialogBuilder.dismiss()
            }


    }

}

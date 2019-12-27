package com.oplayer.orunningplus.function.test

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.DeviceInfo
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.connect.ConnectActivity
import com.oplayer.orunningplus.service.BleService
import com.vicpin.krealmextensions.queryAsFlowable
import com.vicpin.krealmextensions.queryFirst
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import io.realm.rx.ObjectChange
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
           var deviceInfo= it.first()
            message.append("bleName:   ${deviceInfo.bleName}  \n")
            message.append("bleAddress:${deviceInfo.bleAddress}  \n")
            message.append("battery:   ${deviceInfo.battery}  \n")

            tv_message.text = message.toString()
        }


    }

    override fun initView() {

        val tvMessage = findViewById<TextView>(R.id.tv_message)

    }

    override fun initInfo() {


    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btn_conn -> startTo(ConnectActivity::class.java)
            R.id.btn_disconn -> BleService.INSTANCE.disConnBle()
            R.id.btn_find_device ->{EventBus.getDefault().post(MessageEvent(DeviceSetting,DeviceSetting.FIND_DEVICE))}

        }

    }

    override fun onGetEvent(event: MessageEvent) {

    }


}

package com.oplayer.orunningplus.function.test

import android.annotation.SuppressLint
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.oplayer.common.common.DeviceSetting
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.connect.ConnectActivity
import com.oplayer.orunningplus.service.BleService
import com.oplayer.orunningplus.view.DiscreteScroll.DSVOrientation
import com.oplayer.orunningplus.view.DiscreteScroll.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_test.*
import org.greenrobot.eventbus.EventBus

class TestActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_test

//    var message = StringBuilder()

    @SuppressLint("CheckResult")
    override fun initData() {
//        DeviceInfo().queryAsFlowable { queryFirst<DeviceInfo>() }.subscribe {
//           if(it!=null&& it.isNotEmpty()){
//               Slog.d(" 数据变化    ")
//               var deviceInfo = it.first()
//               message.append("bleName:   ${deviceInfo.bleName}  \n")
//               message.append("bleAddress:${deviceInfo.bleAddress}  \n")
//               message.append("battery:   ${deviceInfo.battery}  \n")
//               tv_message.text = message.toString()
//               message = StringBuilder()
//           }
//        }
    }

    override fun initView() {
        initToolbar("TestActivity", true)
//        crv_main.setOrientation(DSVOrientation.VERTICAL)
//        val imgList = mutableListOf<Int>()
//        val mainAdapter = MainAdapter(R.layout.item_card_main, imgList)
//        mainAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
//
//
//        crv_main.adapter = mainAdapter
//        crv_main.setItemTransitionTimeMillis(300)
//        crv_main.setItemTransformer(
//
//            ScaleTransformer.Builder()
//                .setMinScale(0.8f)
//                .build()
//        )


    }

    override fun initInfo() {
    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btn_conn -> startTo(ConnectActivity::class.java)
            R.id.btn_disconn -> BleService.INSTANCE.disConnBle()
            R.id.btn_find_device -> EventBus.getDefault().post(
                MessageEvent(
                    DeviceSetting,
                    DeviceSetting.FIND_DEVICE
                )
            )
            R.id.btn_query_power -> EventBus.getDefault().post(
                MessageEvent(
                    DeviceSetting,
                    DeviceSetting.QUERY_BATTERY
                )
            )
            R.id.btn_chage_skin -> {
//                chcageSkin()
            }

        }
    }

//    private fun chcageSkin() {
//        val colors = UIUtils.getSkinArray()
//        MaterialDialog(this).show {
//            title(R.string.app_name)
//            colorChooser(colors) { dialog, color ->
//                // Use color integer
////                Colorful().edit()
////                    .setPrimaryColor(UIUtils.getSkinThem(color))
////                    .setAccentColor(UIUtils.getSkinThem(color))
////                    .setDarkTheme(false)
////                    .setTranslucent(false)
////                    .apply(UIUtils.getContext()) {
////                        //                        toolbar.setBackgroundColor(color)
////                        dialog.dismiss()
////                    }
//            }
//            positiveButton(R.string.app_name)
//        }
//    }


    override fun onGetEvent(event: MessageEvent) {

    }


}

package com.oplayer.orunningplus

import android.view.View
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder
import com.oplayer.common.common.PermissList
import com.oplayer.common.common.SystemSetting
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.test.TestActivity
import kotlinx.android.synthetic.main.toolbar_common.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initData() {
    }

    override fun initView() {
        //开发阶段 指令测试
        startTo(TestActivity::class.java)
        initToolbar("MainActivity", true)

    }


    override fun onResume() {
        super.onResume()


    }

    override fun initInfo() {
        checkBTState()
        checkNotification()
        checkPermission(PermissList.PERMISSIONS_LIST)
    }

    override fun onClick(v: View) {

        when (v.id) {


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
        Slog.d("检查通知使用权")
        if (isNotificationEnabled()) {
            showToast("通知使用权已经开启  ")
            Slog.d("通知使用权已经开启")
            return
        }
        val dialogBuilder = NiftyDialogBuilder.getInstance(this)

        dialogBuilder.withTitle("通知使用权")
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
        dialogBuilder.show()

    }
}

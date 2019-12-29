package com.oplayer.orunningplus

import android.view.View
import com.oplayer.common.common.PermissList
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
        checkPermission(PermissList.PERMISSIONS_LIST)
    }

    override fun onClick(v: View) {

        when (v.id) {


        }

    }

    override fun onGetEvent(event: MessageEvent) {
    }


}

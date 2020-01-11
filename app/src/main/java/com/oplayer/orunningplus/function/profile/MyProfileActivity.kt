package com.oplayer.orunningplus.function.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.profile.mvp.ProfileContract

class MyProfileActivity : BaseActivity(), ProfileContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
    }

    override fun getLayoutId(): Int {

        return R.layout.activity_my_profile
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {

    }

    override fun onGetEvent(event: MessageEvent) {

    }

    override fun testMessage(message: String) {

    }
}

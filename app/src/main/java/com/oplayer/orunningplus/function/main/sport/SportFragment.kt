package com.oplayer.orunningplus.function.main.today


import androidx.fragment.app.Fragment
import android.view.View
import com.ng.lib_common.base.BaseFragment

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.today.mvp.SportContract
import com.oplayer.orunningplus.function.main.today.mvp.SportPresenter

/**
 * A simple [Fragment] subclass.
 */
class SportFragment : BaseFragment(), SportContract.View {


    lateinit var mPresenter: SportContract.Presenter


    override fun testMessage(message: String) {


    }

    override fun onGetEvent(event: MessageEvent) {


    }

    override fun onClick(v: View) {


    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_sport

    }

    override fun initInjector() {
        mPresenter = SportPresenter()
        mPresenter.attachView(this)
    }

    override fun initView() {

    }

    override fun lazyLoadData() {

     mPresenter.getTestMessage()
    }


    override fun showAlert(message: String, enablePro: Boolean, iconResId: Int, showIcon: Boolean) {



    }

    override fun hideLoading() {


    }

    override fun onError(message: String) {


    }

    override fun onDetach() {
        super.onDetach()
        mPresenter.detachView()
    }

}

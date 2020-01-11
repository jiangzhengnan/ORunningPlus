package com.oplayer.orunningplus.function.profile.mvp

import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView

class ProfileContract {


    interface View : IBaseView {

        fun testMessage(message: String)

    }

    interface Presenter : IBasePresenter<View> {

        fun getTestMessage()


    }

    interface Model{

    }

}
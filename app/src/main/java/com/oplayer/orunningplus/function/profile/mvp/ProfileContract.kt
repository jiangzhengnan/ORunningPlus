package com.oplayer.orunningplus.function.profile.mvp

import android.content.Context
import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView
import com.oplayer.orunningplus.bean.SettingItem

class ProfileContract {


    interface View : IBaseView {


        fun  showSettingItem(list:List<SettingItem>)


    }

    interface Presenter : IBasePresenter<View> {


        fun  getSettingItem(mContext:Context)

    }

    interface Model{
        fun  getSettingItem(mContext: Context):List<SettingItem>
    }
}
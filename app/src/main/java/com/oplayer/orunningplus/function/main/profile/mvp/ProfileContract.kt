package com.oplayer.orunningplus.function.main.profile.mvp

import android.content.Context
import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView
import com.oplayer.orunningplus.bean.SettingItem

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:       com.oplayer.orunningplus.function.main.profile.mvp
 * @ClassName:      ProfileContract
 * @Description:     Profile 契约类
 * @Author:         Ben
 * @CreateDate:     2020/2/1
 */
class ProfileContract {


    interface View : IBaseView {


        fun  showSettingItem(list:List<SettingItem>)







    }

    interface Presenter : IBasePresenter<View> {


        fun  getSettingItem(mContext: Context)

        fun  initUserSettings()

    }

    interface Model{
        fun  getSettingItem(mContext: Context):List<SettingItem>
    }
}
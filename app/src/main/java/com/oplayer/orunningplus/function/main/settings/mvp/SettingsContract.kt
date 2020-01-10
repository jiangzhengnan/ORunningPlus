package com.oplayer.orunningplus.function.main.settings.mvp

import android.content.Context
import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView
import com.oplayer.orunningplus.bean.SettingItem

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.profile.mvp
 * @ClassName:      ProfileContract
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/1/6 17:26
 */
class SettingsContract {

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
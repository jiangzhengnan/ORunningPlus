package com.oplayer.orunningplus.function.main.today.mvp

import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.today
 * @ClassName:      SportContract
 * @Description:    Sport 契约类
 * @Author:         Ben
 * @CreateDate:     2020/1/2 15:34
 */
class SportContract {

    interface View : IBaseView {

        fun testMessage(message: String)

    }

    interface Presenter : IBasePresenter<View> {

        fun getTestMessage()


    }

}
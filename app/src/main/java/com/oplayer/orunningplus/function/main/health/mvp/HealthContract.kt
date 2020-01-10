package com.oplayer.orunningplus.function.main.health.mvp

import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.health.mvp
 * @ClassName:      HealthContract
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/1/6 17:27
 */
class HealthContract {

    interface View : IBaseView {

        fun testMessage(message: String)

    }

    interface Presenter : IBasePresenter<View> {

        fun getTestMessage()


    }
}
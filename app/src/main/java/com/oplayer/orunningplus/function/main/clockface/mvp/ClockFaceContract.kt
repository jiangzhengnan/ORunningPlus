package com.oplayer.orunningplus.function.main.today.mvp

import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView
/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:       com.oplayer.orunningplus.function.main.today.mvp
 * @ClassName:      ClockFaceContract
 * @Description:    ClockFace 契约类
 * @Author:         Ben
 * @CreateDate:     2020/2/7
 */
class ClockFaceContract {

    interface View : IBaseView {

        fun testMessage(message: String)

    }

    interface Presenter : IBasePresenter<View> {

        fun getTestMessage()


    }


    interface Model{
        //获取显示数据

    }

}
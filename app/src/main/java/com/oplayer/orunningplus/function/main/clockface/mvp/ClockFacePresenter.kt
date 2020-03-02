package com.oplayer.orunningplus.function.main.clockface.mvp

import com.oplayer.orunningplus.function.main.today.mvp.ClockFaceContract

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.clockface.mvp
 * @ClassName:      ClockFacePresenter
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/2/7 10:16
 */
class ClockFacePresenter :ClockFaceContract.Presenter {
    protected var mView: ClockFaceContract.View? = null
    protected var mModel: ClockFaceContract.Model

    init {
        mModel = ClockFaceModelImpl()
    }
    override fun getTestMessage() {
      
    }

    override fun attachView(mRootView: ClockFaceContract.View) {
        this.mView = mRootView
    }

    override fun detachView() {
        this.mView =null
    }
}
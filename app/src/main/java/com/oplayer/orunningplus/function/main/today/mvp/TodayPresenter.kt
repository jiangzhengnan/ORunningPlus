package com.oplayer.orunningplus.function.main.today.mvp


/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.today
 * @ClassName:      TodayPresenter
 * @Description:    Today 控制类
 * @Author:         Ben
 * @CreateDate:     2020/1/2 16:28
 */
class TodayPresenter : TodayContract.Presenter {

    protected var mView: TodayContract.View? = null



    override fun attachView(mRootView: TodayContract.View) {
        this.mView = mRootView
    }

    override fun detachView() {
        this.mView = null
    }

}
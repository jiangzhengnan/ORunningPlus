package com.oplayer.orunningplus.function.main.today.mvp

import android.os.Handler
import com.oplayer.common.utils.Slog

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.today
 * @ClassName:      TodayPresenter
 * @Description:    Today 控制类
 * @Author:         Ben
 * @CreateDate:     2020/1/2 16:28
 */
class SportPresenter : SportContract.Presenter {

    protected var mView: SportContract.View? = null

    override fun getTestMessage() {

        Slog.d("UI界面调用  ")
        Handler().postDelayed(Runnable {

            mView?.testMessage(SportModel.getTestMessage())

        }, 1000)

    }


    override fun attachView(mRootView: SportContract.View) {
        this.mView = mRootView
    }

    override fun detachView() {
        this.mView = null
    }

}
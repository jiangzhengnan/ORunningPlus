package com.oplayer.orunningplus.function.advanced.mvp

import android.content.Context
import com.oplayer.orunningplus.function.main.today.mvp.TodayContract

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.advanced.mvp
 * @ClassName:      AdvancedPersion
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/1/9 9:46
 */
class AdvancedPresenter : AdvancedContract.Presenter {


    private val advanModel : AdvancedContract.Model

    init {
        this.advanModel = AdvancedModelImpl()
    }


    protected var mView: AdvancedContract.View? = null
    override fun getSettingItem(mContext: Context) {

      mView?.showSettingItem(advanModel.getAdvancedItem(mContext))

    }

    override fun attachView(mRootView: AdvancedContract.View) {
        mView = mRootView
    }

    override fun detachView() {
        this.mView = null
    }

}
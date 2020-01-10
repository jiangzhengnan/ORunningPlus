package com.oplayer.orunningplus.function.main.settings.mvp

import android.content.Context

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.profile.mvp
 * @ClassName:      ProfilePresenter
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/1/6 17:27
 */
class SettingsPresenter : SettingsContract.Presenter {

    protected var mView: SettingsContract.View? = null
    protected var mModel: SettingsContract.Model

    init {
        mModel = SettingsModelImpl()
    }


    override fun getSettingItem(mContext: Context) {

        mView?.showSettingItem(mModel.getSettingItem(mContext))


    }

    override fun attachView(mRootView: SettingsContract.View) {
        mView = mRootView
    }

    override fun detachView() {
        this.mView = null
    }

}
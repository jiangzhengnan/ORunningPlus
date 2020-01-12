package com.oplayer.orunningplus.function.profile.mvp

import android.content.Context
import com.oplayer.common.utils.Slog

class ProfilePresenter : ProfileContract.Presenter {



    protected var mView: ProfileContract.View? = null
    protected var mModel: ProfileContract.Model

    init {
        mModel = ProfileModelImpl()
    }

    override fun getSettingItem(mContext: Context) {

        Slog.d("getSettingItem   ${ mModel.getSettingItem(mContext).size}")
        mView?.showSettingItem( mModel.getSettingItem(mContext))
    }






    override fun attachView(mRootView: ProfileContract.View) { this.mView=mRootView }
    override fun detachView() { this. mView=null }




}
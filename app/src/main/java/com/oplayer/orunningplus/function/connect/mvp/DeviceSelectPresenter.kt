package com.oplayer.orunningplus.function.connect.mvp

import com.oplayer.common.utils.Slog

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.connect.mvp
 * @ClassName:      DeviceSelectPresenter
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/3/13 11:08
 */
class DeviceSelectPresenter :DeviceSelectContract.Presenter{

     var mView:DeviceSelectContract.View?=null
    protected var mModel: DeviceSelectContract.Model = DeviceSelectModelImpl()

    override fun getDeviceData() {

      var list=  mModel.getDeviceSelectData()
        Slog.d("getDeviceData  ${list.size}")

        mView?.showDeviceDatas(list)
    }

    override fun attachView(mRootView: DeviceSelectContract.View) {
        this.mView=mRootView
    }

    override fun detachView() {
        mView=null
    }


}
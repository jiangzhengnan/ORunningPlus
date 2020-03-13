package com.oplayer.orunningplus.function.connect.mvp

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.oplayer.common.mvp.IBasePresenter
import com.oplayer.common.mvp.IBaseView

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.connect.mvp
 * @ClassName:      DeviceSelectContract
 * @Description:    设备图片选择
 * @Author:         Ben
 * @CreateDate:     2020/3/13 11:07
 */
class DeviceSelectContract {


    interface View : IBaseView {


     fun   showDeviceDatas(list:List<MultiItemEntity>)
    }

    interface Presenter : IBasePresenter<View> {
      fun   getDeviceData()

    }

    interface Model{
        //获取显示数据

        fun  getDeviceSelectData():List<MultiItemEntity>


    }

}
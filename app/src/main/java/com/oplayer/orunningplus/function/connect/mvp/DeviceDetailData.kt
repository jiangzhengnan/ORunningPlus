package com.oplayer.orunningplus.function.connect.mvp

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.oplayer.orunningplus.function.connect.DeviceSelectAdapter

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.connect.mvp
 * @ClassName:      DeviceDetailData
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/3/13 11:09
 */
class DeviceDetailData(var image: Int, var Type: String)

class DeviceDetailDataArr(var list:List<DeviceDetailData>) : MultiItemEntity {


    override fun getItemType(): Int {
        return DeviceSelectAdapter.TYPE_DEVICE_DETAIL
    }

}
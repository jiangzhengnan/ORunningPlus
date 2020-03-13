package com.oplayer.orunningplus.function.connect.mvp

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.oplayer.orunningplus.function.connect.DeviceSelectAdapter

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.connect.mvp
 * @ClassName:      DeviceTitleData
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/3/13 11:09
 */
class DeviceTitleData(var title:String) : MultiItemEntity, AbstractExpandableItem<DeviceDetailDataArr>() {

//    var subTitle=""
//    var title=""





    override fun getLevel(): Int {
        return 1
    }

    override fun getItemType(): Int {
        return DeviceSelectAdapter.TYPE_DEVICE_TITLE
    }
}
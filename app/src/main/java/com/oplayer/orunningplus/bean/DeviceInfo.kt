package com.oplayer.orunningplus.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.provider.bean
 * @ClassName:      DeviceInfo
 * @Description:    封装设备信息
 * @Author:         Ben
 * @CreateDate:     2019/9/5 15:27
 */
@RealmClass
open class DeviceInfo(
    open var bleName: String? = "",
    open var deviceType: String? = "",
    open var bleAddress: String? = "",
    open var battery: String? = "0",
    open var platformCode: String? = "",
    open var isBind: Boolean? = false,
    open var isHandConnect: Boolean? = false

) : RealmObject() {
    @PrimaryKey
    open var id: String? = ""

    override fun toString(): String {
        return "DeviceInfo(bleName=$bleName, deviceType=$deviceType, bleAddress=$bleAddress, battery=$battery, platformCode=$platformCode, isBind=$isBind, isHandConnect=$isHandConnect, id=$id)"
    }


}


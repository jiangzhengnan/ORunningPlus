package com.oplayer.common.utils

import com.oplayer.common.common.DeviceTypeCode
import com.oplayer.common.common.PreferencesKey

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.utils
 * @ClassName:      PreferencesHelper
 * @Description:   存取数据帮助类
 * @Author:         Ben
 * @CreateDate:     2019/10/18 18:05
 */
object PreferencesHelper {


    fun getDeviceType(): String {
        return PreferencesUtil.getString(PreferencesKey.DEVICE_TYPE, DeviceTypeCode.DEVICE_FUNDO)
    }

    fun setDeviceType(type: String) {
        return PreferencesUtil.saveValue(PreferencesKey.DEVICE_TYPE, type)
    }


}
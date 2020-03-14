package com.oplayer.common.utils

import com.oplayer.common.common.DeviceType
import com.oplayer.common.common.PreferencesKey
import com.oplayer.common.common.UnitType

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


    fun getDeviceType(): String = PreferencesUtil.getString(PreferencesKey.DEVICE_TYPE, DeviceType.DEVICE_FUNDO)

    fun setDeviceType(type: String) = PreferencesUtil.saveValue(PreferencesKey.DEVICE_TYPE, type)

    fun isNight(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.IS_NIGHT, false)

    fun setNight(type: Boolean) = PreferencesUtil.saveValue(PreferencesKey.IS_NIGHT, type)


    //默认公制单位
    fun getCurrUnit(): Int = PreferencesUtil.getInt(PreferencesKey.CURR_UNIT, UnitType.UNIT_METRIC)
    fun setCurrUnit(type: String) = PreferencesUtil.saveValue(PreferencesKey.CURR_UNIT, type)



    fun isShowStep(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.IS_SHOW_STEP, true)
    fun setIsShowStep(type: Boolean) = PreferencesUtil.saveValue(PreferencesKey.IS_SHOW_STEP, type)
    fun isShowSleep(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.IS_SHOW_SLEEP, true)
    fun setIsShowSleep(type: Boolean) = PreferencesUtil.saveValue(PreferencesKey.IS_SHOW_SLEEP, type)
    fun isShowHr(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.IS_SHOW_HR, true)
    fun setIsShowHr(type: Boolean) = PreferencesUtil.saveValue(PreferencesKey.IS_SHOW_HR, type)
    fun isShowSport(): Boolean = PreferencesUtil.getBoolean(PreferencesKey.IS_SHOW_SPORT, true)
    fun setIsShowSport(type: Boolean) = PreferencesUtil.saveValue(PreferencesKey.IS_SHOW_SPORT, type)







}
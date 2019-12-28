package com.oplayer.orunningplus.base



/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.common
 * @ClassName:      BleManager
 * @Description:    封装通用方法
 * @Author:         Ben
 * @CreateDate:     2019/12/9 14:57
 */
interface BaseManager {

    /**
     * 当前设备是否连接
     */
    fun isConnected():Boolean

    /**
     * 查找设备
     */
    fun findDevice()


    /**
     * 查找设备电量
     */
    fun queryPower()











    /**
     * 当前指令执行成功
     */
    fun executionSucceed()
    /**
     * 当前指令执行失败
     */
    fun executionFailed()
    /**
     * 当前指令执行中
     */
    fun executionInProgress()



}
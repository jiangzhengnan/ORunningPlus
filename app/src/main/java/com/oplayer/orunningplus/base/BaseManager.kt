package com.oplayer.orunningplus.base

import android.bluetooth.BluetoothDevice
import com.oplayer.common.common.ExecutionStatus
import com.oplayer.orunningplus.bean.NotificationDate
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.service.BleService
import org.greenrobot.eventbus.EventBus


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
    fun isConnected(): Boolean

    /**
     * 查找设备
     */
    fun findDevice()

    /**
     * 查找设备电量
     */
    fun queryPower()


    /**
     * 首页查询指令
     */
    fun todayQuery()


    /**
     * 重置手表
     */

    fun  resetWatch()


    /**
     * 特殊情况下重新链接手表
     */

    fun  reConnect(){
        //不传输设备时 会取当前设备
        BleService.INSTANCE.reScanDevice(null)
    }



    /**
     * 断开手表方法
     */
    fun disConnectBle()

    /**
     * 链接手表通用方法
     */
    fun  bindBle(bluetoothLeDevice: BluetoothDevice)

    /**
     * 当前指令执行成功
     */
    fun executionSucceed() = EventBus.getDefault().post(MessageEvent(ExecutionStatus, ExecutionStatus.EXECUTION_SUCCEED))

    /**
     * 当前指令执行失败
     */
    fun executionFailed() = EventBus.getDefault().post(MessageEvent(ExecutionStatus, ExecutionStatus.EXECUTION_FAILED))

    /**
     * 当前指令执行中
     */
    fun executionInProgress() = EventBus.getDefault().post(MessageEvent(ExecutionStatus, ExecutionStatus.EXECUTION_IN_PROGRESS))


     fun sendNotification(notification: NotificationDate)


}
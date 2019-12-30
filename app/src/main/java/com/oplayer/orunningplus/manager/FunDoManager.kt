package com.oplayer.orunningplus.manager

import android.bluetooth.BluetoothDevice
import android.os.Handler
import com.kct.bluetooth.KCTBluetoothManager
import com.kct.bluetooth.callback.IConnectListener
import com.kct.command.BLEBluetoothManager
import com.kct.command.IReceiveListener
import com.kct.command.KCTBluetoothCommand
import com.oplayer.common.common.FundoCompanyCode
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.OSportApplciation.Companion.sContext
import com.oplayer.orunningplus.base.BaseManager
import com.oplayer.orunningplus.service.BleService
import com.vicpin.krealmextensions.createOrUpdate

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.lib_device
 * @ClassName:      FunDoManager
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2019/9/6 15:39
 */
class FunDoManager private constructor() : BaseManager {


    companion object {
        val instance: FunDoManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FunDoManager()
        }


        val getManager: KCTBluetoothManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            KCTBluetoothManager.getInstance()
        }

    }

    //手表管理实例
    private val kctBluetoothManager = getManager


    /**
     * 使用了仅BLE的sdk kctbleSdk2.jar
     * */
    override fun bindBle(bluetoothLeDevice: BluetoothDevice, any: Any) {
        var iconCallback: IConnectListener = any as IConnectListener
        kctBluetoothManager.registerListener(iconCallback)
        kctBluetoothManager.connect(bluetoothLeDevice)

    }

    override fun isConnected(): Boolean =
        kctBluetoothManager.connectState == KCTBluetoothManager.STATE_CONNECTED


    /**
     * FunDo接收数据
     * */
    fun onReceiveMessage(byteArray: ByteArray?) {
        KCTBluetoothCommand.getInstance().d2a_command_Parse(sContext, byteArray, iReceiveListener)
    }

    private val iReceiveListener =
        IReceiveListener { i, b, objects -> receiveBTDada(i.toByte(), b, objects) }


    /**
     * FunDo接收数据 实际处理位置
     * */
    fun receiveBTDada(type: Byte, response: Boolean, anys: Array<Any>) {
        if (anys[0] is String && anys[0] == "") return

        when (type) {
            FundoCompanyCode.BLE_COMMMAND_SET -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_FIRMWARE_INFO -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_FORTHWITH_STEP -> {
                val step = anys[0] as Int
                val cal = anys[1] as Float
                val distance = anys[2] as Float
                Slog.d("接收当前步数   step $step  cal $cal distance $distance")
            }
            FundoCompanyCode.BLE_KEY_RETURN_FORTHWITH_HR -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_BINDING -> {
            }
            FundoCompanyCode.BLE_KEY_TIME_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_USER_INFO_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_SYSTEM_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_READ_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_ELEC -> {

                Slog.d("接收到电池电量")
                var currDevice = BleService.INSTANCE.getCurrDevice()
                currDevice.battery = anys[0].toString()
                currDevice.createOrUpdate()

                Handler().postDelayed({
                    Slog.d("模拟执行时间")
                    executionSucceed()
                }, 2000);


            }
            FundoCompanyCode.BLE_KEY_RETURN_STEPS -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_SLEEP -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_HR -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_SPORT -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_SPORT_B7 -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_SWIMMING -> {
            }
            FundoCompanyCode.BLE_KEY_SEDENTARY_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_DRINK_WATER_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_DISTURB_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_HEART_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_ALARM_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_LOOKUP_PHONE -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_END_CALL -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_FIRMWARE_UPDATE -> {
            }
            FundoCompanyCode.BLE_KEY_UNIT_SETTING -> {
            }
            FundoCompanyCode.BLE_KEY_CAMERA_OPEN -> {
            }
            FundoCompanyCode.BLE_KEY_CAMERA_CLOSE -> {
            }
            FundoCompanyCode.BLE_COMMMAND_REMIND -> {
            }
            FundoCompanyCode.BLE_COMMMAND_DEVICE -> {
            }
            else -> {
                Slog.d("未知命令")
            }
        }


    }


    /**
     * === 主动下发指令
     * */

    /**
     * 查找设备
     * */
    override fun findDevice() =
        sendInstruction(BLEBluetoothManager.BLE_COMMAND_a2d_findDevice_pack())

    /**
     * 查询设备电量
     * */
    override fun queryPower() =
        sendInstruction(BLEBluetoothManager.BLE_COMMAND_a2d_getBatteryStatus_pack())

    /**
     * 重置设备
     * */
    override fun resetWatch() =
        sendInstruction(BLEBluetoothManager.BLE_COMMAND_a2d_sendReset_pack())

    override fun disConnectBle(any: Any) {
        try {
            val iconCallback = any as IConnectListener
            kctBluetoothManager.disConnect_a2d()
            kctBluetoothManager.unregisterListener(iconCallback)
        } catch (e: Exception) {
            Slog.e("取消链接失败  回调类型错误   $e")
        }
    }

    /**
     * 发送指令方法
     * */

    fun sendInstruction(array: ByteArray) {
        kctBluetoothManager.sendCommand_a2d(array)
        executionInProgress()
    }

}
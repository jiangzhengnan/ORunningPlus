package com.oplayer.orunningplus.BleManager

import com.kct.bluetooth.KCTBluetoothManager
import com.kct.bluetooth.bean.BluetoothLeDevice
import com.kct.bluetooth.callback.IConnectListener
import com.kct.command.IReceiveListener
import com.kct.command.KCTBluetoothCommand
import com.oplayer.common.common.FundoCompanyCode
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.OSportApplciation.Companion.sContext
import com.oplayer.orunningplus.base.BaseManager
import com.oplayer.orunningplus.bean.DeviceInfo

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
    }


    /**
     * 使用了仅BLE的sdk kctbleSdk2.jar
     * */
     fun bindBle(btDevice: DeviceInfo, iconCallback: IConnectListener) {
        Slog.d("连接设备    : ${btDevice}")
        KCTBluetoothManager.getInstance().registerListener(iconCallback)
        KCTBluetoothManager.getInstance()?.connect(btDevice.bluetoothDevice)

    }


     fun isConnected(): Boolean = KCTBluetoothManager.getInstance().connectState == KCTBluetoothManager.STATE_CONNECTED




     fun unBindBle(iconCallback: IConnectListener) {
        KCTBluetoothManager.getInstance()?.disConnect_a2d()
        KCTBluetoothManager.getInstance().unregisterListener(iconCallback)


    }

    fun onReceiveMessage(byteArray: ByteArray?) {
        KCTBluetoothCommand.getInstance().d2a_command_Parse(sContext, byteArray, iReceiveListener)
    }

    private val iReceiveListener =
        IReceiveListener { i, b, objects -> receiveBTDada(i.toByte(), b, objects) }

    fun receiveBTDada(type: Byte, response: Boolean, vararg `object`: Any) {
        when (type) {
            FundoCompanyCode.BLE_COMMMAND_SET -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_FIRMWARE_INFO -> {
            }
            FundoCompanyCode.BLE_KEY_RETURN_FORTHWITH_STEP -> {
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

}
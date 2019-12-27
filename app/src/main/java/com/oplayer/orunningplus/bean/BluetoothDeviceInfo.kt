package  com.oplayer.orunningplus.bean

import android.bluetooth.BluetoothDevice
import com.polidea.rxandroidble2.RxBleDevice
import com.polidea.rxandroidble2.scan.ScanResult

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.lib_device.bean
 * @ClassName:      BTDevice
 * @Description:    封装蓝牙连接对象 因为DeviceInfo 需要存储到realm 无法存储蓝牙对象
 * @Author:         Ben
 * @CreateDate:     2019/7/26 10:01
 */
open class BluetoothDeviceInfo (

    open  var  scanResult: ScanResult,
    open  var  deviceType: String ?=null

) {
    override fun toString(): String {
        return "BluetoothDeviceInfo(scanResult=$scanResult, deviceType=$deviceType)"
    }
}



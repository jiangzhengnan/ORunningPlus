package com.oplayer.orunningplus.service

import android.app.Service
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.ParcelUuid
import com.kct.bluetooth.KCTBluetoothManager
import com.kct.bluetooth.bean.BluetoothLeDevice
import com.kct.bluetooth.callback.IConnectListener
import com.oplayer.common.base.BaseService
import com.oplayer.common.common.*
import com.oplayer.common.utils.NotifiUtils
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils

import com.oplayer.orunningplus.BleManager.FunDoManager
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.DeviceInfo
import com.oplayer.orunningplus.common.RxBleFactory
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.javautils.JavaUtil
import com.oplayer.orunningplus.utils.ThreadPoolManager
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import com.vicpin.krealmextensions.createOrUpdate
import com.vicpin.krealmextensions.queryFirst
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableHelper.dispose
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus


class BleService : BaseService() {


    val TAG = "BleService"

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    val sContext = UIUtils.getContext()
    private var scanDisposable: Disposable? = null
    var mDevice: MutableList<ScanResult> = mutableListOf()
    private var instance: BleService? = null

    override fun onCreate() {
        super.onCreate()
//        startService()
    }

    companion object {
        val INSTANCE: BleService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BleService()
        }
    }


    public fun startService() {

        if (!isRunning()) {
            val intent = Intent(sContext, BleService::class.java)
            Slog.d("启动服务 ")
            if (Build.VERSION.SDK_INT >= 26) {
                sContext.startForegroundService(intent)
            } else {
                sContext.startService(intent)
            }
        } else {
            Slog.d("不启动服务 ")
        }
    }

    fun isRunning(): Boolean {
        return AppManager.instance.isServiceRunning(sContext, BleService::class.java.name)

    }

    public fun stopBleService() {
        if (isRunning()) {
            val intent = Intent(sContext, BleService::class.java)
            sContext.stopService(intent)
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        instance = this

        var contextText = UIUtils.getString(R.string.server_contextText)
        chageContextText(contextText)

        checkConnState()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun chageContextText(str: String) {
        var cls = OSportApplciation.javaClass
        openForegroundService(
            UIUtils.getContext(),
            R.mipmap.ic_launcher,
            str,
            cls
        )
    }

    //检测连接是否成功
    private fun checkConnState() {


        var deviceInfo = getCurrDevice()

        if (deviceInfo.isBind == true && isConnected()) {
            //已经绑定但是没有连接

            reScanDevice(deviceInfo)


        }

    }


    public fun scanDevice(scanFilter: ScanFilter) {
        mDevice.clear()
        RxBleFactory(scanFilter).getObservable()
            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
            .doFinally { dispose() }
            .subscribe({
                //   Slog.d("添加设备  ")
                var scanResult = it
                if (!scanResult.bleDevice?.name.isNullOrEmpty()) {
                    var isFound = false
                    mDevice.forEach {
                        if (it.bleDevice.macAddress.equals(scanResult.bleDevice.macAddress)) {
                            //重复设备
                            isFound = true
                        }
                    }
                    if (!isFound) {
                        mDevice.add(scanResult)
                        EventBus.getDefault()
                            .post(
                                MessageEvent(
                                    ScanDeviceState.SCAN_RESULT,
                                    DeviceInfo(scanResult.bleDevice.bluetoothDevice, DeviceTypeCode.DEVICE_UNKNOWN)
                                )
                            )
                    }
                }
            }, {
                Slog.d("搜索蓝牙出错  $it")
                EventBus.getDefault().post(MessageEvent(ScanDeviceState.SCAN_ERROR, it))
            })
            .let {
                scanDisposable = it
            }
    }


    public fun reScanDevice(deviceInfo: DeviceInfo) {


           RxBleFactory(deviceInfo).getObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally { dispose() }
            .subscribe({
                val scanResult = it
                if (!scanResult.bleDevice?.name.isNullOrEmpty()) {
                    Slog.d("搜索到指定设备  重新连接")

                    deviceInfo.deviceType


                }
            }, {
                Slog.d("重新搜索蓝牙出错  $it")
            })
            .let {
                scanDisposable = it
            }
    }


//    private fun IdentifyTypes(device: ScanResult): BluetoothDeviceInfo {
//        var recordDate = JavaUtil.parseData(device.scanRecord.getServiceUuids())
//        var deviceType = DeviceTypeCode.DEVICE_FUNDO
//        var uuids = recordDate.getUuids()
//        uuids.forEach {
//            when (it) {
//                DeviceUUID.FUNDO_BLE_YDS_UUID -> {
//                    Slog.d("发现分动设备")
//                    deviceType = DeviceTypeCode.DEVICE_FUNDO
//                }
//                else -> {
////                    Slog.d(" 未知设备类型  ${recordDate.getManufacturer()} ")
//                    deviceType = DeviceTypeCode.DEVICE_UNKNOWN
//                }
//            }
//
//        }
//
//        return BluetoothDeviceInfo(device, deviceType)
//
//    }

    public fun stopScanDevice() {
        mDevice.clear()
        scanDisposable?.dispose()
        scanDisposable = null
    }

    private fun dispose() {

        if (scanDisposable!!.isDisposed) {
            scanDisposable?.dispose()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        scanDisposable?.dispose()


    }


    //分动数据连接回调
    var iconCallback = object : IConnectListener {
        override fun onConnectState(state: Int) {
            Slog.d("  onConnectState    $state")
            when (state) {
                KCTBluetoothManager.STATE_CONNECTED -> {
                    Slog.d("连接成功")
                    ConnectStatus(BluetoothState.CONNECTION_SUCCESS)
                }
                KCTBluetoothManager.STATE_NONE,
                KCTBluetoothManager.STATE_CONNECT_FAIL -> {
                    Slog.d("连接失败")
                    ConnectStatus(BluetoothState.CONNECTION_FAILED)

                }
                KCTBluetoothManager.STATE_CONNECTING -> {
                    Slog.d("连接中")
                    ConnectStatus(BluetoothState.CONNECTIONNTING)
                }

            }
        }

        override fun onCommand_d2a(byteArray: ByteArray?) {
            Slog.d("  onCommand_d2a  接收数据  $byteArray")
            FunDoManager.instance.onReceiveMessage(byteArray)

        }

        override fun onConnectDevice(device: BluetoothDevice?) {
            Slog.d("  onConnectDevice    ${device?.name}")
        }

        override fun onScanDevice(device: BluetoothLeDevice?) {
            Slog.d("  onScanDevice  搜索到设备  ${device?.name}")
        }


    }


    /**
     *多协议下 统一连接状态
     * */

    private fun ConnectStatus(state: String) {

        when (state) {
            BluetoothState.CONNECTION_SUCCESS -> {
                /**
                 * device 当前点击连接设备
                 * */
                var deviceInfo = getCurrDevice()
                deviceInfo.bleName = device?.bluetoothDevice?.name
                deviceInfo.bleAddress =device?.bluetoothDevice?.address
                deviceInfo.isBind = true
                deviceInfo.createOrUpdate()
                EventBus.getDefault().post(
                    MessageEvent(
                        Constants.BLUETOOTH_MESSAGE,
                        BluetoothState.CONNECTION_SUCCESS
                    )
                )
            }
            BluetoothState.CONNECTION_FAILED -> {
                EventBus.getDefault().post(
                    MessageEvent(
                        Constants.BLUETOOTH_MESSAGE,
                        BluetoothState.CONNECTION_FAILED
                    )
                )
            }
            BluetoothState.CONNECTIONNTING -> {
                EventBus.getDefault()
                    .post(MessageEvent(Constants.BLUETOOTH_MESSAGE, BluetoothState.CONNECTIONNTING))
            }

        }


    }

    override fun onGetEvent(event: MessageEvent) {
        when (event.getMessageType()) {
            Constants.BLUETOOTH_DEVICE -> {
                var deviceInfo = event.getMessage() as DeviceInfo
                connBle(deviceInfo)
            }
            DeviceSetting.FIND_DEVICE -> {
            }
        }
    }

    /**
     * 连接分发
     * */
    public fun connBle(btDevice: DeviceInfo) {

        when (btDevice.deviceType) {
            DeviceTypeCode.DEVICE_UNKNOWN -> {
                //todo 添加配对指令之后无法发现服务 无法分辨设备类型
                device = btDevice
                Slog.d("分动设备执行连接 ")
                FunDoManager.instance.bindBle(btDevice, iconCallback)
            }
            //添加多种协议设备
            else -> {
//                Slog.d("未知设备类型   $btDevice ")
            }
        }
    }


    /**
     * 维护当前连接设备
     * */
    var device: DeviceInfo? = null

    fun getCurrDevice(): DeviceInfo {
        if (device === null) {
            var deviceByRealm = DeviceInfo().queryFirst()
            if (deviceByRealm == null) {
                device = DeviceInfo()
            } else {
                this.device = deviceByRealm
            }
        }
        return device as DeviceInfo
    }

    fun setBind(isBind: Boolean) {
        var deviceInfo = getCurrDevice()
        deviceInfo.isBind = isBind
        deviceInfo.createOrUpdate()
    }


    fun isConnected(): Boolean {
        when (getCurrDevice().deviceType) {
            DeviceTypeCode.DEVICE_FUNDO -> {
                return FunDoManager.instance.isConnected()
            }
            else -> {
                Slog.d("未知设备类型 ")
            }
        }

        return false
    }

    //分发重新连接
    fun reConnected(): Boolean {

        when (getCurrDevice().deviceType) {
            DeviceTypeCode.DEVICE_FUNDO -> {
                return FunDoManager.instance.isConnected()
            }
            else -> {
                Slog.d("未知设备类型 ")
            }
        }

        return false
    }


}




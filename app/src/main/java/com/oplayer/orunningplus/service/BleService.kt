package com.oplayer.orunningplus.service

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.widget.Toast
import com.kct.bluetooth.KCTBluetoothManager
import com.kct.bluetooth.bean.BluetoothLeDevice
import com.kct.bluetooth.callback.IConnectListener
import com.oplayer.common.base.BaseService
import com.oplayer.common.common.*
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils

import com.oplayer.orunningplus.manager.FunDoManager
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.BluetoothDeviceInfo
import com.oplayer.orunningplus.bean.DeviceInfo
import com.oplayer.orunningplus.common.RxBleFactory
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.utils.javautils.JavaUtil
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.vicpin.krealmextensions.createOrUpdate
import com.vicpin.krealmextensions.queryFirst
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import java.lang.Exception
import java.util.*


class BleService : BaseService() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    val sContext = UIUtils.getContext()
    private var scanDisposable: Disposable? = null
    private val isScanning: Boolean get() = scanDisposable != null
    var mDevice: MutableList<ScanResult> = mutableListOf()
    private var instance: BleService? = null

    override fun onCreate() {
        super.onCreate()

        startService()

    }

    companion object {
        val INSTANCE: BleService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            BleService()
        }


    }

    /**
     * 维护当前连接设备
     * */
    var device: DeviceInfo? = null

    fun getCurrDevice(): DeviceInfo {
        if (device == null) {
            var deviceByRealm = DeviceInfo().queryFirst()
            if (deviceByRealm == null) {
                device = DeviceInfo()
            } else {
                this.device = deviceByRealm
            }
        }
        return device as DeviceInfo
    }

    /**
     * 启动服务方法
     * */
    private fun startService() {

        if (!isRunning()) {
            val intent = Intent(sContext, BleService::class.java)
            Slog.d("执行服务器启动方法")
            if (Build.VERSION.SDK_INT >= 26) {
                sContext.startForegroundService(intent)
            } else {
                sContext.startService(intent)
            }
        } else {
            Slog.d("服务已在运行   ")
        }
    }

    /**
     * 终止服务方法
     * */
     fun stopBleService() {
        if (isRunning()) {
            val intent = Intent(sContext, BleService::class.java)
            sContext.stopService(intent)
        }
    }

    /**
     * 判断服务是否运行
     * */
    fun isRunning(): Boolean {
        return AppManager.instance.isServiceRunning(sContext, BleService::class.java.name)
    }

    /**
     * 判断启动成功
     * */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        instance = this
        var contextText = UIUtils.getString(R.string.server_contextText)
        chageContextText(contextText)
        checkConnState()
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 检测连接是否成功
     */
    private fun checkConnState() {
        var deviceInfo = getCurrDevice()
        if (deviceInfo.isBind == true && !isConnected()) {
            //更新绑定状态，非手动连接
            setBind(true, false)
            //已经绑定但是没有连接
            reScanDevice(deviceInfo)
        }
    }

    /**
     * 修改服务通知信息方法
     */
    private fun chageContextText(str: String) {
        var cls = OSportApplciation.javaClass
        openForegroundService(
            UIUtils.getContext(),
            R.mipmap.ic_launcher,
            str,
            cls
        )
    }

    /**
     * 蓝牙搜索方法
     * scanFilter 搜索过滤器
     * */
    public fun scanDevice(scanFilter: ScanFilter) {


        if (isScanning) {
            //如果处于搜索状态，则清空已搜索到的设备 不重新开始搜索 搜索频繁会导致搜索失败
//            scanDisposable?.dispose()
            mDevice.clear()
            Slog.d("正在搜索中")
        } else {

            RxBleFactory(scanFilter).getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { dispose() }
                .subscribe({
                    var scanResult = it
                    if (!scanResult.bleDevice?.name.isNullOrEmpty()) {
                        var isFound = false
                        mDevice.forEach {
                            if (it.bleDevice.macAddress == scanResult.bleDevice.macAddress) { /*重复设备*/ isFound =
                                true
                            }
                        }
                        if (!isFound) {
                            mDevice.add(scanResult)
                            EventBus.getDefault().post(
                                MessageEvent(
                                    ScanDeviceState.SCAN_RESULT,
                                    BluetoothDeviceInfo(scanResult, DeviceType.DEVICE_FUNDO)
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
    }

    /**
     * 重新搜索蓝牙方法 根据设备名称  mac地址构建搜索过滤器搜索
     * scanFilter 搜索过滤器
     * */
    public fun reScanDevice(deviceInfo: DeviceInfo) {
        RxBleFactory(deviceInfo).getObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doFinally { dispose() }
            .subscribe({
                val scanResult = it
                if (!scanResult.bleDevice?.name.isNullOrEmpty()) {
                    Slog.d("搜索到指定设备 ${scanResult.bleDevice.name}  address ${scanResult.bleDevice.macAddress} ")
                    connBle(BluetoothDeviceInfo(scanResult, getCurrDevice().deviceType))
                }
            }, {
                Slog.d("重新搜索蓝牙出错  $it")
            })
            .let {
                scanDisposable = it
            }
    }

    /**
     * 停止搜索
     * */
    public fun stopScanDevice() {
        mDevice.clear()
        scanDisposable?.dispose()
        scanDisposable = null
    }

    /**
     * 协议识别方法   分动添加配对指令之后无法识别
     * */
    private fun IdentifyTypes(device: ScanResult): BluetoothDeviceInfo {
        var recordDate = JavaUtil.parseData(device.scanRecord.bytes)
        var deviceType = DeviceType.DEVICE_FUNDO
        var uuids = recordDate.getUuids()
        uuids.forEach {
            when (it) {
                DeviceUUID.FUNDO_BLE_YDS_UUID -> {
                    Slog.d("发现分动设备")
                    deviceType = DeviceType.DEVICE_FUNDO
                }
                else -> {
//                    Slog.d(" 未知设备类型  ${recordDate.getManufacturer()} ")
                    deviceType = DeviceType.DEVICE_UNKNOWN
                }
            }

        }

        return BluetoothDeviceInfo(device, deviceType)

    }

    /**
     * 蓝牙搜索客户端终止
     */
    private fun dispose() {
        if (scanDisposable!!.isDisposed) {
            scanDisposable?.dispose()
        }
    }

    /**
     * 服务销毁回调
     */
    override fun onDestroy() {
        super.onDestroy()
        scanDisposable?.dispose()
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
                device = getCurrDevice()
                device!!.isBind = true
                device!!.isHandConnect = true
                device!!.createOrUpdate()
                EventBus.getDefault().post(
                    MessageEvent(
                        Constants.BLUETOOTH_MESSAGE,
                        BluetoothState.CONNECTION_SUCCESS
                    )
                )
                chageContextText(UIUtils.getString(R.string.device_state_success) + device?.bleName)
            }
            BluetoothState.CONNECTION_FAILED -> {
                EventBus.getDefault().post(
                    MessageEvent(
                        Constants.BLUETOOTH_MESSAGE,
                        BluetoothState.CONNECTION_FAILED
                    )
                )
                chageContextText(UIUtils.getString(R.string.device_state_failed) + device?.bleName)
                //断开连接 清空消息队列
                executionQueue.clear()
            }
            BluetoothState.CONNECTIONNTING -> {
                EventBus.getDefault()
                    .post(MessageEvent(Constants.BLUETOOTH_MESSAGE, BluetoothState.CONNECTIONNTING))
                chageContextText(UIUtils.getString(R.string.device_state_connectionning))

            }

        }


    }

    /**
     * 连接分发
     * */
    public fun connBle(bluetoothDeviceInfo: BluetoothDeviceInfo) {
        //连接设备的时候应该停止搜索 并延时连接
        stopScanDevice()
        Handler().postDelayed({
            when (bluetoothDeviceInfo.deviceType) {
                DeviceType.DEVICE_FUNDO -> {
                    //todo 添加配对指令之后无法发现服务 无法分辨设备类型
                    var bleDevice = bluetoothDeviceInfo.scanResult.bleDevice.bluetoothDevice
                    //准备连接设备  电量 适配号 绑定状态未知
                    device = getCurrDevice()
                    device!!.bleName = bleDevice.name
                    device!!.bleAddress = bleDevice.address
                    device!!.deviceType = DeviceType.DEVICE_FUNDO
                    Slog.d("分动设备执行连接 ")
                    FunDoManager.instance.bindBle(bleDevice, iconCallback)
                }
                //添加多种协议设备
                else -> {
                    Slog.d("未知设备类型  ")
                }
            }

        }, 500)


    }

    /**
     * 断开连接分发
     * */
    public fun disConnBle() {
        when (getCurrDevice().deviceType) {
            DeviceType.DEVICE_FUNDO -> {
                Slog.d("DEVICE FUNDO DISCONNECT BLE")
                FunDoManager.instance.disConnectBle(iconCallback)
            }
            //添加多种协议设备
            else -> {
                Slog.d("未知设备类型  ")
            }
        }
        setBind(false, true)
    }

    /**
     * EventBus 消息回调
     * */
    override fun onGetEvent(event: MessageEvent) {
        when (event.getMessageType()) {
            //分发设备链接
            Constants.BLUETOOTH_DEVICE -> {
                var deviceInfo = event.getMessage() as BluetoothDeviceInfo
                connBle(deviceInfo)
            }
            //分发设置设备指令
            DeviceSetting -> {
                controllingDevice(event.getMessage() as String)
            }
            //指令执行回调
            ExecutionStatus -> {
                detectionExecutionStatus(event.getMessage() as String)
            }
            //通知消息接收 分发
            NotifiPackName->{
                //传入包名 消息内容 按协议分发
                detectionNotification(event.getMessage() as String)

            }


        }
    }

    private fun detectionNotification(notification: String) {

    }

    /**
     * 指令执行回调
     * */
    private fun detectionExecutionStatus(status: String) {
        when (status) {
            ExecutionStatus.EXECUTION_SUCCEED -> {
                Slog.d("指令执行成功  方法：  ${currentOrder.toString()}  time:  ${System.currentTimeMillis()}")
                endOrder()
            }

            ExecutionStatus.EXECUTION_IN_PROGRESS -> {
                Slog.d("指令执行中  方法：  ${currentOrder.toString()}")

            }

            ExecutionStatus.EXECUTION_FAILED -> {
                Slog.d("指令执行失败  方法：  ${currentOrder.toString()}")

            }

        }
    }

    /**
     * 分发对设备指令
     * */
    private fun controllingDevice(message: String) {
        if (!isConnected()) {
            Slog.d("设备未连接 无法发送消息")
            Toast.makeText(
                this,
                UIUtils.getString(R.string.device_state_not_conn),
                Toast.LENGTH_LONG
            ).show()

        }


        when (message) {
            //查找设备指令
            DeviceSetting.FIND_DEVICE -> {
                Slog.d("发送查找设备指令")
                when (getCurrDevice().deviceType) {
                    DeviceType.DEVICE_FUNDO -> addFunc { FunDoManager.instance.findDevice() }
                    else -> {
                    }
                }
            }

            //查询设备电量指令
            DeviceSetting.QUERY_BATTERY -> {
                Slog.d("发送查找设备指令")
                when (getCurrDevice().deviceType) {
                    DeviceType.DEVICE_FUNDO -> addFunc { FunDoManager.instance.queryPower() }
                    else -> {
                    }
                }
            }


            else -> {

            }
        }

    }

    /**
     *快速设置设备是否绑定 是否连接
     * */
    fun setBind(isBind: Boolean, isHandConn: Boolean) {
        var deviceInfo = getCurrDevice()
        deviceInfo.isBind = isBind
        deviceInfo.isHandConnect = isHandConn
        deviceInfo.createOrUpdate()
    }

    /**
     *多协议下 请求链接状态
     * */
    fun isConnected(): Boolean {
        when (getCurrDevice().deviceType) {
            DeviceType.DEVICE_FUNDO -> {
                return FunDoManager.instance.isConnected()
            }
            else -> {
                Slog.d("未知设备类型 ")
            }
        }

        return false
    }


    /**
     * FunDo数据连接回调
     */
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
//            Slog.d("  onCommand_d2a  接收数据  $byteArray")
            FunDoManager.instance.onReceiveMessage(byteArray)

        }

        override fun onConnectDevice(device: BluetoothDevice?) {
            Slog.d("  onConnectDevice    ${device?.name}")
        }

        override fun onScanDevice(device: BluetoothLeDevice?) {
            Slog.d("  onScanDevice  搜索到设备  ${device?.name}")
        }
    }


/***********************使用队列存储命令 - start******************************/
    //队列存储方法
    private var executionQueue = LinkedList<Order>()
    // 当前任务
    private var currentOrder: (() -> Unit?)? = null
    //添加任务到队列
    fun addFunc(function: () -> Unit?) {
        var order = Order(function)
        doOrder(order)
    }
    //结束任务
    fun endOrder() {
        doOrder(null)
    }
    //执行任务
    private fun doOrder(order: Order?) {
        if (order != null) {
            executionQueue.offer(order)
        } else {
            currentOrder = null
        }
        if (currentOrder == null) {
            if (executionQueue.size != 0) {
                var funnow = executionQueue.poll()
                currentOrder = funnow.function
                currentOrder?.invoke()
            }
        }
    }
    //存储任务
    data class Order(var function: () -> Unit? = {})

/***********************使用队列存储命令 - stop******************************/



}




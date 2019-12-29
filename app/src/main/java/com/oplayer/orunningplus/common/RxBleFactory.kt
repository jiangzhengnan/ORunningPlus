package com.oplayer.orunningplus.common

import android.os.ParcelUuid
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.orunningplus.bean.DeviceInfo
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanResult
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.Observable

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.common
 * @ClassName:      ScanFilter
 * @Description:    封装过滤条件
 * @Author:         Ben
 * @CreateDate:     2019/12/10 10:02
 */
class RxBleFactory {
    var uuid = ""
    lateinit var deviceInfo: DeviceInfo
    lateinit var scanFilter: ScanFilter

    /**
     * 返回默认搜索过滤
     * */
    constructor() {
        this.scanFilter = ScanFilter.Builder()
            .build()
    }

    /**
     * 设置UUID返回搜索过滤
     * */
    constructor(uuid: String) {
        this.uuid = uuid
        this.scanFilter = ScanFilter.Builder()
            .setServiceUuid(ParcelUuid.fromString(uuid))
            .build()
    }

    /**
     * 传入过滤条件
     * */
    constructor(scanFilter: ScanFilter) {
        this.scanFilter = scanFilter
    }

    /**
     * 用于重新搜索 根据用户名和mac地址搜索指定设备
     * */
    constructor(deviceInfo: DeviceInfo) {
        this.deviceInfo = deviceInfo
        this.scanFilter = ScanFilter.Builder()
            .setDeviceName(deviceInfo.bleName)
            .setDeviceAddress(deviceInfo.bleAddress)
            .build()
    }


    fun getObservable(): Observable<ScanResult> {
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        return OSportApplciation.rxBleClient.scanBleDevices(
            scanSettings,
            scanFilter
        )
    }



}
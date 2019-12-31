package com.oplayer.orunningplus.manager

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.htsmart.wristband2.WristbandApplication
import com.htsmart.wristband2.WristbandManager
import com.htsmart.wristband2.bean.BatteryStatus
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.base.BaseManager
import com.oplayer.orunningplus.service.BleService
import com.vicpin.krealmextensions.createOrUpdate
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.manager
 * @ClassName:      FitCloudManager
 * @Description:     FitCloud管理设备
 * @Author:         Ben
 * @CreateDate:     2019/12/30 20:59
 */

class FitCloudManager private constructor() : BaseManager {


    companion object {
        val instance: FitCloudManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FitCloudManager()
        }

        val getWristbandManager: WristbandManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            WristbandApplication.getWristbandManager()
        }


    }

    //手表管理实例
    private val wristbandManager = getWristbandManager


    override fun isConnected(): Boolean = wristbandManager.isConnected


    @SuppressLint("CheckResult")
    override fun findDevice() {
        wristbandManager.findWristband()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Slog.d("查找设备  ")
                    executionSucceed()
                },
                { throwable: Throwable ->
                    run {
                        executionFailed()
                        Slog.d("查找设备错误 $throwable")
                    }

                }
            )

    }

    override fun queryPower() {
        wristbandManager.requestBattery()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<BatteryStatus> {
                override fun onSubscribe(d: Disposable) {
                    Slog.d("onSubscribe  $d")
                }

                override fun onSuccess(batteryStatus: BatteryStatus) {
                    Slog.d("getBatteryCount  " + batteryStatus.batteryCount + "   getPercentage " + batteryStatus.percentage)
                    var currDevice = BleService.INSTANCE.getCurrDevice()
                    currDevice.battery = batteryStatus.percentage.toString()
                    currDevice.createOrUpdate()
                    executionSucceed()
                }

                override fun onError(e: Throwable) {
                    Slog.d("queryPower onError  $e")
                    executionFailed()
                }
            })
    }

    @SuppressLint("CheckResult")
    override fun resetWatch() {
        if (isConnected()) {
            wristbandManager.resetWristband()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Slog.d("重置手环  ")
                    // TODO: 2019/12/30   重置手环之后需要重新链接



                }, { throwable -> Slog.d(" 重置手环错误  $throwable") })
        } else {
            Slog.d("设备未连接")
        }

    }

    override fun disConnectBle(any: Any) {
        if (isConnected()) {
            wristbandManager.close()
        } else {
            Slog.d("设备未连接")
        }

    }

    override fun bindBle(bluetoothLeDevice: BluetoothDevice, any: Any) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
package com.oplayer.orunningplus.manager

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.htsmart.wristband2.WristbandApplication
import com.htsmart.wristband2.WristbandManager
import com.htsmart.wristband2.bean.BatteryStatus
import com.htsmart.wristband2.bean.ConnectionState
import com.htsmart.wristband2.bean.WristbandNotification
import com.oplayer.common.common.BluetoothState
import com.oplayer.common.common.NotifiDate
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.base.BaseManager
import com.oplayer.orunningplus.bean.NotificationDate
import com.oplayer.orunningplus.service.BleService
import com.vicpin.krealmextensions.createOrUpdate
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
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

    override fun disConnectBle() {
        if (isConnected()) {
            wristbandManager.close()
        } else {
            Slog.d("设备未连接")
        }

    }

    @SuppressLint("CheckResult")
    override fun bindBle(bluetoothLeDevice: BluetoothDevice) {

        var id = "1"
        var isBind = true
        var isSex = true
        var age = 18
        var height = 180F
        var weight = 180F

        wristbandManager.connect(bluetoothLeDevice, id, !isBind, isSex, age, height, weight)
        wristbandManager.observerConnectionState().subscribe(
            Consumer {
                when (it) {
                    ConnectionState.CONNECTED -> {
                        Slog.d(" FitCloud 连接成功")
                        BleService.INSTANCE.ConnectStatus(BluetoothState.CONNECTION_SUCCESS)
                    }
                    ConnectionState.DISCONNECTED -> {

                        Slog.d("连接失败")
                        BleService.INSTANCE.ConnectStatus(BluetoothState.CONNECTION_FAILED)

                    }
                    else -> {
                        Slog.d("连接中")
                        BleService.INSTANCE.ConnectStatus(BluetoothState.CONNECTIONNTING)
                    }
                }

            }

        )

    }

    @SuppressLint("CheckResult")
    override fun sendNotification(notification: NotificationDate) {
        Slog.d("fit Cloud  准备发送通知消息  $notification ")
        var  type=WristbandNotification.TYPE_OTHERS_APP
        when (notification.pkg) {
            NotifiDate.APP_PACKAGE_QQ         -> {  type=  WristbandNotification.TYPE_QQ                  }
            NotifiDate.APP_PACKAGE_WECHAT     -> {  type=  WristbandNotification.TYPE_WECHAT              }
            NotifiDate.APP_PACKAGE_WHATSAPP   -> {  type=  WristbandNotification.TYPE_WHATSAPP            }
            NotifiDate.APP_PACKAGE_MESSENGER  -> {  type=  WristbandNotification.TYPE_FACEBOOK_MESSENGER  }
            NotifiDate.APP_PACKAGE_TWITTER    -> {  type=  WristbandNotification.TYPE_TWITTER             }
            NotifiDate.APP_PACKAGE_LINKEDIN   -> {  type=  WristbandNotification.TYPE_LINKEDIN            }
            NotifiDate.APP_PACKAGE_INSTAGRAM  -> {  type=  WristbandNotification.TYPE_INSTAGRAM           }
            NotifiDate.APP_PACKAGE_FACEBOOK   -> {  type=  WristbandNotification.TYPE_FACEBOOK            }
            NotifiDate.APP_PACKAGE_SMS        -> {  type=  WristbandNotification.TYPE_SMS                 }
            NotifiDate.APP_PACKAGE_LINE       -> {  type=  WristbandNotification.TYPE_LINE                }
            NotifiDate.APP_PACKAGE_VIBER      -> {  type=  WristbandNotification.TYPE_VIBER               }
            NotifiDate.APP_PACKAGE_SKYPE      -> {  type=  WristbandNotification.TYPE_SKYPE               }
            NotifiDate.APP_PACKAGE_OUTLOOK    -> {  type=  WristbandNotification.TYPE_OTHERS_APP          }

        }
        val notifi = WristbandNotification()
        notifi.type = type
        notifi.name = notification.contentTitle
        notifi.content = notification.contentText

        wristbandManager
            .sendWristbandNotification(notifi)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Slog.d("通知发送  $notifi") },
                { throwable -> Slog.d(" notification send error  $throwable") })

    }

}
package com.oplayer.common.common

import android.Manifest
import android.content.Intent
import java.util.*

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.common
 * @ClassName:      Constants
 * @Description:    静态常量类
 * @Author:         Ben
 * @CreateDate:     2019/7/24 9:46
 */

//定制包名
class CustomizedPackName {
    companion object {
        var ORunningPlus="com.oplayer.orunningplus"

    }


}



class Constants {
    companion object {

        const val REALM_VERSION = 1L
        const val OPLAYER_LOG = "oplayer_error_log"
        const val BLUETOOTH_MESSAGE = "BLUETOOTH_MESSAGE"
        const val BLUETOOTH_DEVICE = "BLUETOOTH_DEVICE"
        const val DEVICE_SETTING = "DEVICE_SETTING"

    }
}




class TodayDateType {
    companion object {
     const  val STEP = 0
     const  val HEART = 1
     const  val SLEEP = 2
     const  val SPORT = 3
    }
}


class SleepDateType {
    companion object {
        const  val light = 0
        const  val deep = 1
        const  val wake = 2
    }
}

class SecurityKey {
    companion object {
        const val SECURE_PREFERENCES_KEY = "com.oplayer"
        const val REALM_KEY = "com.oplayer.comm"
    }


}



class PreferencesKey {
    companion object {
        const val DEVICE_TYPE = DeviceType.DEVICE_FUNDO
        const val IS_NIGHT = "IS_NIGHT"


    }
}

class DeviceSetting {
    companion object {
        const val TODAY_QUERY = "TODAY_QUERY"
        const val FIND_DEVICE = "FIND_DEVICE"
        const val QUERY_BATTERY = "QUERY_BATTERY"
    }


}


class DeviceType {
    companion object {
        const val DEVICE_FUNDO = "DEVICE_FUNDO"
        const val DEVICE_FITCLOUD = "DEVICE_FITCLOUD"
        const val DEVICE_UNKNOWN = "DEVICE_UNKNOWN"

    }


}

class BluetoothState {
    companion object {
        const val CONNECTION_SUCCESS = "CONNECTION_SUCCESS"
        const val CONNECTION_FAILED = "CONNECTION_FAILED"
        const val CONNECTIONNTING = "CONNECTIONNTING"
    }


}

class ScanDeviceState {
    companion object {
        const val SCAN_START = "SCAN_START"
        const val SCAN_STOP = "SCAN_STOP"
        const val SCAN_RESULT = "SCAN_RESULT"
        const val SCAN_ERROR = "SCAN_ERROR"
    }


}


class NotifiDate {
    companion object {

        const val APP_PACKAGE_QQ            = "com.tencent.mobileqq"
        const val APP_PACKAGE_WECHAT        = "com.tencent.mm"
        const val APP_PACKAGE_WHATSAPP      = "com.whatsapp"
        const val APP_PACKAGE_MESSENGER     = "com.facebook.orca"
        const val APP_PACKAGE_TWITTER        = "com.twitter.android"
        const val APP_PACKAGE_LINKEDIN       = "com.linkedin.android"
        const val APP_PACKAGE_INSTAGRAM      = "com.instagram.android"
        const val APP_PACKAGE_FACEBOOK      = "com.facebook.katana"
        const val APP_PACKAGE_SMS           = "com.android.mms"
        const val APP_PACKAGE_LINE           = "jp.naver.line.android"
        const val APP_PACKAGE_VIBER          = "com.viber.voip"
        const val APP_PACKAGE_SKYPE          = "com.skype.raider"
        const val APP_PACKAGE_OUTLOOK         = "com.microsoft.office.outlook"

    }


}





class DeviceUUID() {
    companion object {
        //过滤非手环的蓝牙设备
        val FUNDO_BLE_YDS_UUID =
            UUID.fromString("0000FEA0-0000-1000-8000-00805f9b34fb") as UUID//分动

         //仿苹果设备没有UUID 用公司识别码判断
        val FITCLOULD_TYPE: Int = 0x5448

    }
}

class CompanyCode() {
    companion object {
        val FUNDO_BLE_CODE = 0xFEA0 as Short

    }
}

class ExecutionStatus {
    companion object {
        val EXECUTION_SUCCEED = "EXECUTION_SUCCEED"
        val EXECUTION_FAILED = "EXECUTION_FAILED"
        val EXECUTION_IN_PROGRESS = "EXECUTION_IN_PROGRESS"

    }

}

class FundoCompanyCode {
    companion object {
        //手环命令
        val BLE_PROTOCOL_MARK = 0XBA.toByte()                  //分动手环协议识别标志
        val BLE_COMMMAND_FIRMWARE_UPGRADE = 0x01.toByte()                  //固件升级命令
        val BLE_COMMMAND_SET = 0x02.toByte()                  //设置命令   //发送Flash数据开始命令返回
        val BLE_COMMMAND_WEATHER = 0x03.toByte()                  //天气命令
        val BLE_COMMMAND_DEVICE = 0x04.toByte()                  //设备命令    //发送Flash数据响应返回
        val BLE_COMMMAND_LOOKUP = 0x05.toByte()                  //查找命令
        val BLE_COMMMAND_REMIND = 0x06.toByte()                  //提醒命令  //查询Flash数据版本号返回
        val BLE_COMMMAND_SPORTS = 0x07.toByte()                  //运动模式命令
        val BLE_COMMMAND_SLEEP = 0x08.toByte()                  //睡眠命令
        val BLE_COMMMAND_HEART_RATE = 0x09.toByte()                  //心率命令
        val BLE_COMMMAND_SYNCHRO = 0x0A.toByte()                  //同步命令
        val BLE_COMMMAND_CALIBRATION = 0x0B.toByte()                  //校准命令
        val BLE_COMMMAND_FACTORY = 0x0C.toByte()                  //工厂命令
        val BLE_COMMMAND_PUSH = 0x0D.toByte()                  //推送数据到手机
        val BLE_KEY_RETURN_FIRMWARE_INFO = 0x13.toByte()                  //固件信息返回
        val BLE_KEY_RETURN_FIRMWARE_UPDATE = 0x11.toByte()                  //固件升级返回
        val BLE_KEY_REQUEST_ELEC = 0x40.toByte()                  //电量请求
        val BLE_KEY_RETURN_ELEC = 0x41.toByte()                  //电量返回
        val BLE_KEY_REQUEST_RELIEVE = 0x42.toByte()                  //解除绑定请求
        val BLE_KEY_RETURN_RELIEVE = 0x43.toByte()                  //解除绑定返回
        val BLE_KEY_REQUEST_BINDING = 0x44.toByte()                  //设备绑定请求
        val BLE_KEY_RETURN_BINDING = 0x45.toByte()                  //设备绑定返回
        val BLE_KEY_RETURN_BONDAUTH = 0x01045600.toByte()                  //手被授权返回
        val BLE_KEY_CAMERA_OPEN = 0x46.toByte()                  //打开相机
        val BLE_KEY_CAMERA_COMMMAND = 0x47.toByte()                  //拍照
        val BLE_KEY_CAMERA_CLOSE = 0x48.toByte()                  //关闭相机
        val BLE_KEY_DISPLAY_COMMMAND = 0x49.toByte()                  //横竖显示
        val BLE_KEY_UNIT_SETTING = 0x01.toByte()                  //单位设置
        val BLE_KEY_TIME_SETTING = 0x20.toByte()                  //时间设置
        val BLE_KEY_ALARM_SETTING = 0x21.toByte()                  //闹钟设置
        val BLE_KEY_SPORTS_GOAL_SETTING = 0x22.toByte()                  //运动目标设置
        val BLE_KEY_USER_INFO_SETTING = 0x23.toByte()                  //用户信息设置
        val BLE_KEY_ANTI_LOST_SETTING = 0x24.toByte()                  //防丢设置
        val BLE_KEY_SEDENTARY_SETTING = 0x25.toByte()                  //久坐设置
        val BLE_KEY_AUTO_SLEEP_SETTING = 0x26.toByte()                  //自动睡眠设置
        val BLE_KEY_SYSTEM_SETTING = 0x27.toByte()                  //系统用户设置
        val BLE_KEY_DRINK_WATER_SETTING = 0x28.toByte()                  //喝水提醒设置
        val BLE_KEY_NEWS_PUSH_SETTING = 0x29.toByte()                  //消息推送设置
        val BLE_KEY_REQUEST_READ_CONFIG = 0x2C.toByte()                  //读取配置请求
        val BLE_KEY_RETURN_READ_CONFIG = 0x2D.toByte()                  //读取配置返回
        val BLE_KEY_REQUEST_READ_SETTING = 0x2E.toByte()                  //读取设置请求
        val BLE_KEY_RETURN_READ_SETTING = 0x2F.toByte()                  //读取设置返回
        val BLE_KEY_LOOKUP_DEVICE = 0x50.toByte()                  //查找设备
        val BLE_KEY_LOOKUP_PHONE = 0x51.toByte()                  //查找手机
        val BLE_KEY_REQUEST_STEP = 0x70.toByte()                  //计步数据请求
        val BLE_KEY_RETURN_STEP = 0x71.toByte()                  //计步数据返回
        val BLE_KEY_RETURN_SPEED = 0x72.toByte()                  //心率、速度、步频数据返回
        val BLE_KEY_RETURN_PACE = 0x72.toByte()                  //配速数据返回
        val BLE_KEY_RETURN_SLEEP = 0xA2.toByte()                  //睡眠数据返回
        val BLE_KEY_RETURN_STEPS = 0xA3.toByte()                  //记步数据返回
        val BLE_KEY_RETURN_HR = 0xA4.toByte()                  //心率数据返回
        val BLE_KEY_RETURN_SPORT = 0xA5.toByte()                  //运动数据返回
        val BLE_KEY_RETURN_SPORT_B7 = 0xB7.toByte()                  //运动数据返回
        val BLE_KEY_RETURN_SWIMMING = 0xB6.toByte()                  //游泳模式
        val BLE_KEY_RETURN_FORTHWITH_HR = 0xAB.toByte()                  //实时心率数据返回
        val BLE_KEY_RETURN_FORTHWITH_STEP = 0xAC.toByte()                  //实时运动数据
        val BLE_KEY_RETURN_FORTHWITH_BLOOD = 0xAE.toByte()                  //实时血压血氧数据返回
        val BLE_KEY_RETURN_DISTURB_SETTING = 0x64.toByte()       //勿扰设置返回
        val BLE_KEY_RETURN_HEART_SETTING = 0x92.toByte()       //心率提醒设置返回
        val BLE_KEY_RETURN_END_CALL = 0x010D0200.toByte()       //挂断电话

    }
}

/**
 * 系统界面
 * */
class SystemSetting{
    companion object{
       //通知使用界面
        val NOTIFICATION_LISTENER_INTENT = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")


    }

}

class PermissList {
    companion object {

        var PERMISSIONS_LIST = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    }

}


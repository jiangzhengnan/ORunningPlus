package com.oplayer.orunningplus

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.StrictMode
import com.facebook.stetho.Stetho
import com.htsmart.wristband2.WristbandApplication
import com.kct.bluetooth.KCTBluetoothManager
import com.oplayer.common.common.*
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.service.BleService
import com.oplayer.orunningplus.utils.javautils.Utils
import com.polidea.rxandroidble2.LogConstants
import com.polidea.rxandroidble2.LogOptions
import com.polidea.rxandroidble2.RxBleClient
import com.squareup.leakcanary.LeakCanary
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.multimoon.colorful.Defaults
import io.multimoon.colorful.ThemeColor
import io.multimoon.colorful.initColorful
import io.realm.Realm
import io.realm.RealmConfiguration
import java.security.SecureRandom


/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common
 * @ClassName:      OSportApplciation
 * @Description:
 * @Author:         Ben
 * @CreateDate:     2019/7/23 11:24
 */

class OSportApplciation : Application() {


    companion object {
        lateinit var sContext: Context
        lateinit var rxBleClient: RxBleClient
    }

    override fun onCreate() {
        super.onCreate()
        Slog.d("当前工作模式 debug? :  ${BuildConfig.DEBUG}")

        sContext = this
        initSkin()
        initLog()
        initRealm()
        initStetho()
        initRxBleClient()
        initSDK()
        initStrictMode()
        initBTService()
        initLeakCanary()


    }

    /**
     * 初始化内存泄漏检测工具
     * */
    fun initLeakCanary() {

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

    }


    /**
     * 启动蓝牙管理通用服务
     * */
    private fun initBTService() {
        var isRunning =
            AppManager.instance.isServiceRunning(this, BleService()::class.java.name)
        val intent = Intent(this, BleService().javaClass)
        if (!isRunning) {
            Slog.d("启动服务 ")
            if (Build.VERSION.SDK_INT >= 26) {
                this.startForegroundService(intent)
            } else {
                this.startService(intent)
            }
        } else {
            Slog.d("不启动服务 ")
        }


    }

    /**
     * 初始化Realm测试工具
     * */
    private fun initStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build()
        )
    }

    private val DEV_MODE = true

    /**
     * 初始化严格模式
     * */
    private fun initStrictMode() {
        if (DEV_MODE) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyDialog() //弹出违规提示对话框
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .penaltyFlashScreen() //API等级11
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    /**
     * 初始化蓝牙sdk Fundo
     * */
    private fun initSDK() {

        var initSdklist: MutableList<String> = mutableListOf()

        when (packageName) {
            CustomizedPackName.ORunningPlus -> {
                initSdklist.add(DeviceType.DEVICE_FUNDO)
                initSdklist.add(DeviceType.DEVICE_FITCLOUD)
            }
            else -> {
            }
        }






        initSdklist.forEach {
            when (it) {
                DeviceType.DEVICE_FUNDO -> {
                    KCTBluetoothManager.getInstance().init(this)
                }
                DeviceType.DEVICE_FITCLOUD -> {

                    WristbandApplication.init(this)
                    WristbandApplication.setDebugEnable(BuildConfig.DEBUG)
                }
                else -> {
                }
            }

        }


    }


    /**
     * 初始化蓝牙框架 RxBle
     * */
    private fun initRxBleClient() {
        rxBleClient = RxBleClient.create(this)
        RxBleClient.updateLogOptions(
            LogOptions.Builder()
                .setLogLevel(LogConstants.INFO)
                .setMacAddressLogSetting(LogConstants.MAC_ADDRESS_FULL)
                .setUuidsLogSetting(LogConstants.UUIDS_FULL)
                .setShouldLogAttributeValues(true)
                .build()
        )
    }


    /**
     * 初始化主题框架
     * */
    private fun initSkin() {
        val defaults: Defaults = Defaults(
            primaryColor = ThemeColor.GREEN,
            accentColor = ThemeColor.BLUE,
            useDarkTheme = false,
            translucent = false
        )
        initColorful(this, defaults)
    }

    /**
     * 初始化Realm数据库
     * */
    private fun initRealm() {
        Realm.init(this)
        val realmKey = Utils.getRealmKey(SecurityKey.REALM_KEY) as ByteArray

        SecureRandom().nextBytes(realmKey)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
                .name("oplayer.realm")
                .encryptionKey(realmKey)//指定数据库的密钥。
                .schemaVersion(Constants.REALM_VERSION)
                //开发阶段数据库改动频繁 采用删除升级
                .deleteRealmIfMigrationNeeded()
                // .migration(Migration())  //数据库升级
                // .assetFile("oplayer.realm")
                .build()
        )


    }

    /**
     * 初始化Log
     * */
    private fun initLog() {
        Slog.getSettings()
            .setLogEnable(true)
            .setBorderEnable(true)
    }


}
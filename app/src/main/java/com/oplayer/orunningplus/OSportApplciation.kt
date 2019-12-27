package com.oplayer.orunningplus

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import com.oplayer.common.common.AppManager
import com.oplayer.common.common.Constants
import com.oplayer.common.utils.Slog
import com.polidea.rxandroidble2.LogConstants
import com.polidea.rxandroidble2.LogOptions
import com.polidea.rxandroidble2.RxBleClient
import io.multimoon.colorful.Defaults
import io.multimoon.colorful.ThemeColor
import io.multimoon.colorful.initColorful
import io.realm.Realm
import io.realm.RealmConfiguration
import android.os.StrictMode
import android.provider.SyncStateContract
import com.facebook.stetho.Stetho
import com.kct.bluetooth.KCTBluetoothManager

import com.oplayer.orunningplus.service.BleService
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




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
        sContext = this
        initSkin()
        initLog()
        initRealm()
        initStetho()
        initRxBleClient()
        initSDK()
        initStrictMode()
        initBTService()

    }


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


    private fun initStetho() {
        Stetho.initialize(
            Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .build())
    }

    private val DEV_MODE = true
    private fun initStrictMode() {
        if (DEV_MODE) {
//            StrictMode.setThreadPolicy(
//                StrictMode.ThreadPolicy.Builder()
//                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectNetwork()   // or .detectAll() for all detectable problems
//                    .penaltyDialog() //弹出违规提示对话框
//                    .penaltyLog() //在Logcat 中打印违规异常信息
//                    .penaltyFlashScreen() //API等级11
//                    .build()
//            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    private fun initSDK() {
        KCTBluetoothManager.getInstance().init(this)
    }


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

    private fun initSkin() {
        val defaults: Defaults = Defaults(
            primaryColor = ThemeColor.GREEN,
            accentColor = ThemeColor.BLUE,
            useDarkTheme = false,
            translucent = false
        )
        initColorful(this, defaults)
    }




    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(
            RealmConfiguration.Builder()
            .name("oplayer.realm")
            .schemaVersion(Constants.REALM_VERSION)
            .deleteRealmIfMigrationNeeded()
//            .migration(Migration())
//            .assetFile("oplayer.realm")
            .build())
    }

    private fun initLog() {
        Slog.getSettings()
            .setLogEnable(true)
            .setBorderEnable(true)

    }


}
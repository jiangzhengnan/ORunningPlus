package com.oplayer.orunningplus.function.main.settings.mvp

import android.content.Context
import android.content.Intent
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.advanced.AdvancedSettingActivity
import com.oplayer.orunningplus.function.connect.ConnectActivity
import com.oplayer.orunningplus.function.test.TestActivity
import org.greenrobot.eventbus.EventBus

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.profile.mvp
 * @ClassName:      ProfileModel
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/1/6 17:26
 */
class SettingsModelImpl : SettingsContract.Model {

    //构建设置也选项  测试阶段跳转testactivity
    override fun getSettingItem(mContext: Context): List<SettingItem> = mutableListOf(
        SettingItem(
            R.mipmap.settings_connect,
            UIUtils.getString(R.string.settings_conn),false
        ) { mContext.startActivity(Intent(mContext, ConnectActivity::class.java)) }
        ,
        SettingItem(
            R.mipmap.settings_find,
            UIUtils.getString(R.string.settings_find),false,
            DeviceSetting.FIND_DEVICE
        )

        ,
        SettingItem(
            R.mipmap.settings_12format,
            UIUtils.getString(R.string.settings_12Hour),true,null
        )
        ,
        SettingItem(
            R.mipmap.settings_notification,
            UIUtils.getString(R.string.settings_notifi),false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }
        ,
        SettingItem(
            R.mipmap.settings_camera,
            UIUtils.getString(R.string.settings_camera),false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }
        ,
        SettingItem(
            R.mipmap.settings_googlefit,
            UIUtils.getString(R.string.settings_googlefit),true,null
        )
        ,
        SettingItem(
            R.mipmap.settings_weather,
            UIUtils.getString(R.string.settings_weather),false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }
        ,
        SettingItem(
            R.mipmap.today_settings,
            UIUtils.getString(R.string.settings_advanced),false
        ) { mContext.startActivity(Intent(mContext, AdvancedSettingActivity::class.java)) }
        ,
        SettingItem(
            R.mipmap.settings_appstability,
            UIUtils.getString(R.string.settings_stability),false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }
        ,
        SettingItem(
            R.mipmap.settings_reset,
            UIUtils.getString(R.string.settings_reset),false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }
        ,
        SettingItem(
            R.mipmap.settings_firmware,
            UIUtils.getString(R.string.settings_firmware),false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }


    )

}
package com.oplayer.orunningplus.function.advanced.mvp

import android.content.Context
import android.content.Intent
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.function.advanced.AdvancedSettingActivity
import com.oplayer.orunningplus.function.connect.ConnectActivity
import com.oplayer.orunningplus.function.test.TestActivity

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.advanced.mvp
 * @ClassName:      AdvancedModelImpl
 * @Description:    构建高级设置页选项
 * @Author:         Ben
 * @CreateDate:     2020/1/9 10:01
 */
class AdvancedModelImpl : AdvancedContract.Model {
    override fun getAdvancedItem(mContext: Context): List<SettingItem> = mutableListOf(
        SettingItem(
            R.mipmap.settings_alarm,
            UIUtils.getString(R.string.settings_alarm),
            false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) },
        SettingItem(
            R.mipmap.settings_sedentary,
            UIUtils.getString(R.string.settings_sedentary),
            false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) },
        SettingItem(
            R.mipmap.settings_drink,
            UIUtils.getString(R.string.settings_drinking),
            false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) },
        SettingItem(
            R.mipmap.settings_disturb,
            UIUtils.getString(R.string.settings_disturb),
            false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) },
        SettingItem(
            R.mipmap.settings_smartwakeup,
            UIUtils.getString(R.string.settings_wake_up),
            false
        ) { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }


    )


}
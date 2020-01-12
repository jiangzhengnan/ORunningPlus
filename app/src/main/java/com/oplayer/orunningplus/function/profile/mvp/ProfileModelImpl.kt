package com.oplayer.orunningplus.function.profile.mvp

import android.content.Context
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SettingItem

class ProfileModelImpl : ProfileContract.Model {


    //构建设置也选项  测试阶段跳转testactivity
    override fun getSettingItem(mContext: Context): List<SettingItem> = mutableListOf(
        SettingItem(R.mipmap.my_profile_gender, UIUtils.getString(R.string.profile_gender),false)                                               {""},
        SettingItem(R.mipmap.my_profile_birthday, UIUtils.getString(R.string.profile_birthday),false,                                          {""}),
        SettingItem(R.mipmap.my_profile_weight, UIUtils.getString(R.string.profile_weirht),false,                                              {""}),
        SettingItem(R.mipmap.my_profile_height, UIUtils.getString(R.string.profile_heightr),false,                                               {""}),
        SettingItem(R.mipmap.my_profile_bp, UIUtils.getString(R.string.profile_bloodr),false,                                          {""}),
        SettingItem(R.mipmap.information_goal, UIUtils.getString(R.string.today_goal),false,                                                 {""}),
        SettingItem(R.mipmap.settings_googlefit, UIUtils.getString(R.string.settings_googlefit),true,                               {""}),
        SettingItem(R.mipmap.information_theme, UIUtils.getString(R.string.profile_them),false,                                                {""} ),
        SettingItem(R.mipmap.settings_appstability, UIUtils.getString(R.string.settings_stability),false,                          {""}),
        SettingItem(R.mipmap.information_privacy, UIUtils.getString(R.string.profile_policyr),false,                                    {""})


    )

}
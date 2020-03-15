package com.oplayer.orunningplus.function.main.profile.mvp

import android.content.Context
import android.content.Intent
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.function.test.TestActivity

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.profile.mvp
 * @ClassName:      ProfilePresenter
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/2/1 11:58
 */
class ProfilePresenter :ProfileContract.Presenter {

    protected var mView:ProfileContract.View? = null
    //    protected var mModel: ProfileContract.Model
    lateinit var settingsItem: List<SettingItem>


    override fun getSettingItem(mContext: Context) {
        getSettingData(mContext)
        mView?.showSettingItem(settingsItem)
    }

    private fun getSettingData(mContext: Context) {
        settingsItem = mutableListOf(

            SettingItem(
                R.mipmap.information_goal,
                UIUtils.getString(R.string.profile_goals),
                false,
                {  mContext.startActivity(Intent(mContext, TestActivity::class.java))  }),

            SettingItem(
                R.mipmap.information_goal,
                UIUtils.getString(R.string.profile_unit),
                false,
                { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }),

            SettingItem(
                R.mipmap.information_theme,
                UIUtils.getString(R.string.profile_them),
                false,
                { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }),
            SettingItem(
                R.mipmap.information_privacy,
                UIUtils.getString(R.string.profile_policyr),
                false,
                { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }),
            SettingItem(
                R.mipmap.information_faq,
                UIUtils.getString(R.string.profile_feedback),
                false,
                { mContext.startActivity(Intent(mContext, TestActivity::class.java)) }),
            SettingItem(
                R.mipmap.information_about,
                UIUtils.getString(R.string.profile_about),
                false,
                { mContext.startActivity(Intent(mContext, TestActivity::class.java)) })

        )




    }


    override fun initUserSettings() {
    }

    override fun attachView(mRootView: ProfileContract.View) {
        this.mView = mRootView
    }



    override fun detachView() {
        this.mView = null
    }





}
package com.oplayer.orunningplus.function.main.profile


import android.app.Activity
import android.content.Intent
import android.os.Environment
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.oplayer.orunningplus.base.BaseFragment
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.bean.UserInfo
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.profile.activity.UserInfoActivity
import com.oplayer.orunningplus.function.main.profile.mvp.ProfileContract
import com.oplayer.orunningplus.function.main.profile.mvp.ProfilePresenter
import com.oplayer.orunningplus.function.main.settings.SettingsAdapter
import com.oplayer.orunningplus.service.BleService
import com.vicpin.krealmextensions.createOrUpdate
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment(), ProfileContract.View {


    var mPresenter: ProfileContract.Presenter = ProfilePresenter()
    var settingsList = mutableListOf<SettingItem>()
    lateinit var settingsAdapter: SettingsAdapter

    override fun getLayoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun initInjector() {
        mPresenter = ProfilePresenter()
        mPresenter.attachView(this)

    }


    override fun initView() {
        initToolbar(UIUtils.getString(R.string.main_profile))
        initRecycleView()

        profile_image.setOnClickListener { startTo(activity, UserInfoActivity::class.java) }
        tv_user_name.setOnClickListener { startTo(activity, UserInfoActivity::class.java) }

    }

    override fun onResume() {
        super.onResume()

        if (tv_user_name != null) {
            var path = BleService.INSTANCE.getCurrUser().iconPath
            var name = BleService.INSTANCE.getCurrUser().name
            if (path != null) Glide.with(this).load(path).into(profile_image)
            if (name != null) tv_user_name.text = name

            Slog.d("图片选择界面路径   $path")
        }

    }


    override fun lazyLoadData() {

        mPresenter.getSettingItem(mActivity)

        mPresenter.initUserSettings()

    }


    private fun initRecycleView() {

        settingsAdapter = SettingsAdapter(R.layout.item_settings, settingsList)
        settingsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
        rv_myProfile.layoutManager = LinearLayoutManager(mActivity)
        rv_myProfile.adapter = settingsAdapter
        settingsAdapter.setOnItemClickListener { adapter, view, position ->
            var settingItem = adapter.data[position] as SettingItem
            when (settingItem.function) {
                null -> {
                    Slog.d("该项不执行方法  ")

                }
                else -> {

                    var function = settingItem.function as () -> Unit
                    function.invoke()


                }

            }


        }

    }


    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }


    override fun onGetEvent(event: MessageEvent) {
        when (event.getMessageType()) {
            else -> {
                Slog.d("未知消息  ")
            }
        }

    }


    override fun showSettingItem(list: List<SettingItem>) {
        settingsList = list as MutableList<SettingItem>
        settingsAdapter.setNewData(settingsList)
        rv_myProfile.adapter = settingsAdapter
    }


    override fun hideLoading() {
    }

    override fun onError(message: String) {
    }


}

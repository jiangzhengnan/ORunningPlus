package com.oplayer.orunningplus.function.main.settings


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.oplayer.orunningplus.base.BaseFragment
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils

import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.settings.mvp.SettingsContract
import com.oplayer.orunningplus.function.main.settings.mvp.SettingsPresenter
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.fragment_settings.*
import org.greenrobot.eventbus.EventBus
import rx.functions.Function

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment(), SettingsContract.View {

    lateinit var settingsAdapter: SettingsAdapter
    lateinit var mPresenter: SettingsContract.Presenter


    override fun onGetEvent(event: MessageEvent) {
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_settings
    }

    override fun initInjector() {
        mPresenter = SettingsPresenter()
        mPresenter.attachView(this)

    }


    override fun onDetach() {
        super.onDetach()
        if(this::mPresenter.isInitialized){
            mPresenter.detachView()
        }


    }
    override fun initView() {
        initToolbar(UIUtils.getString(R.string.main_settings))
        initRecycleView()
    }

    private fun initRecycleView() {
        var settingsList = mutableListOf<SettingItem>()
        settingsAdapter = SettingsAdapter(R.layout.item_settings, settingsList)


        settingsAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)


        rv_settings.layoutManager = LinearLayoutManager(mActivity)
        settingsAdapter.setOnItemChildClickListener { adapter, view, position ->
            var switchButton = view.findViewById<SwitchButton>(R.id.sb_item)
            Slog.d(" switchButton.isChecked  ${switchButton.isChecked}")
         return@setOnItemChildClickListener
        }




        settingsAdapter.setOnItemClickListener { adapter, view, position ->
            var settingItem = adapter.data[position] as SettingItem
            when (settingItem.function) {
                null -> {
                    Slog.d("该项不执行方法  ")
                }
                is String -> {
                    Slog.d("点击字符方法  $settingItem.function")
                    var stringFun = settingItem.function as String

                    when (stringFun) {
                        DeviceSetting.FIND_DEVICE -> {
                            EventBus.getDefault()
                                .post(MessageEvent(DeviceSetting, DeviceSetting.FIND_DEVICE))
                        }
                        else -> {
                        }
                    }

                }
                else -> {
                    var function = settingItem.function as () -> Unit
                    Slog.d("点击可执行方法   $function")
                    function.invoke()
                }
            }


        }
        rv_settings.adapter = settingsAdapter
    }

    override fun lazyLoadData() {
        mPresenter.getSettingItem(mActivity)
    }


    override fun showSettingItem(list: List<SettingItem>) {
        Slog.d("showSettingItem  显示设置页列表 $list")
        settingsAdapter.setNewData(list)
    }


    override fun showAlert(message: String, enablePro: Boolean, iconResId: Int, showIcon: Boolean) {

    }

    override fun hideLoading() {

    }

    override fun onError(message: String) {

    }


}

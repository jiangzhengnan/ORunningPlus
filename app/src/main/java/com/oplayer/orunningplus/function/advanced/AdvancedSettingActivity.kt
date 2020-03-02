package com.oplayer.orunningplus.function.advanced

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.oplayer.common.common.DeviceSetting
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.advanced.mvp.AdvancedContract
import com.oplayer.orunningplus.function.advanced.mvp.AdvancedPresenter
import com.oplayer.orunningplus.function.main.settings.SettingsAdapter
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.activity_advanced_setting.*
import org.greenrobot.eventbus.EventBus

class AdvancedSettingActivity : BaseActivity(),AdvancedContract.View {

    lateinit var settingsAdapter: SettingsAdapter
    var mPresenter: AdvancedContract.Presenter = AdvancedPresenter()


    init {
        mPresenter.attachView(this)
    }
    
    override fun getLayoutId(): Int {
        return R.layout.activity_advanced_setting
    }

    override fun initData() {
        mPresenter.getSettingItem(this)
    }

    override fun initView() {
        initToolbar(UIUtils.getString(R.string.settings_advanced),true)
        initRecycleView()
    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {
//test
    }

    override fun onGetEvent(event: MessageEvent) {

    }

    private fun initRecycleView() {
        var settingsList = mutableListOf<SettingItem>()
        settingsAdapter = SettingsAdapter(R.layout.item_settings, settingsList)
        settingsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        rv_advanced.layoutManager = LinearLayoutManager(this)

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
        rv_advanced.adapter = settingsAdapter
    }

    override fun showSettingItem(list: List<SettingItem>) {
        Slog.d("showSettingItem  显示设置页列表 $list")
        settingsAdapter.setNewData(list)
        rv_advanced.adapter = settingsAdapter
    }


}

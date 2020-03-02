package com.oplayer.orunningplus.function.main

import android.view.View
import com.oplayer.common.common.TodayDateType
import com.oplayer.common.utils.PreferencesHelper
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.suke.widget.SwitchButton
import kotlinx.android.synthetic.main.activity_manage.*
import org.greenrobot.eventbus.EventBus

class ManageActivity : BaseActivity(), SwitchButton.OnCheckedChangeListener {


    override fun getLayoutId(): Int {
        return R.layout.activity_manage
    }

    override fun initData() {
        sb_step_item.isChecked=PreferencesHelper.isShowStep()
        sb_sport_item.isChecked=PreferencesHelper.isShowSport()
        sb_sleep_item.isChecked=PreferencesHelper.isShowSleep()
        sb_hr_item   .isChecked=PreferencesHelper.isShowHr()

    }

    override fun initView() {
        initToolbar(UIUtils.getString(R.string.today_manage), true)
        sb_step_item.setOnCheckedChangeListener(this)
        sb_sport_item.setOnCheckedChangeListener(this)
        sb_sleep_item.setOnCheckedChangeListener(this)
        sb_hr_item.setOnCheckedChangeListener(this)
//        sb_step_item.setOnCheckedChangeListener  { view, isChecked -> PreferencesHelper.setIsShowStep(isChecked) }
//        sb_sport_item.setOnCheckedChangeListener { view, isChecked -> PreferencesHelper.setIsShowSport(isChecked) }
//        sb_sleep_item.setOnCheckedChangeListener { view, isChecked -> PreferencesHelper.setIsShowSleep(isChecked) }
//        sb_hr_item    .setOnCheckedChangeListener    { view, isChecked -> PreferencesHelper.setIsShowHr(isChecked) }




    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {

    }


    override fun onGetEvent(event: MessageEvent) {
//        when (event.getMessageType()) {
//            R.id.sb_item -> {
//                Slog.d("switch 按钮状态变换  ${event.getMessage() as Boolean}  ")
//            }
//            else -> {
//                Slog.d("未知消息  ")
//            }
//
//        }

    }

    override fun onCheckedChanged(view: SwitchButton?, isChecked: Boolean) {

        when (view?.id) {
           R.id.sb_step_item  -> {  PreferencesHelper.setIsShowStep(isChecked) }
           R.id.sb_sport_item  -> {  PreferencesHelper.setIsShowSport(isChecked) }
           R.id.sb_sleep_item  -> {  PreferencesHelper.setIsShowSleep(isChecked) }
           R.id.sb_hr_item  ->    {  PreferencesHelper.setIsShowHr(isChecked) }

        }
//        EventBus.getDefault().postSticky(MessageEvent(TodayDateType.MAIN_CARD_CHAGE))
        EventBus.getDefault().post(MessageEvent(TodayDateType.MAIN_CARD_CHAGE))
Slog.d(" 发送布局消息  ")
    }


}

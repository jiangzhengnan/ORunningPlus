package com.oplayer.orunningplus.function.main.settings

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SettingItem
import com.oplayer.orunningplus.event.MessageEvent
import com.suke.widget.SwitchButton
import org.greenrobot.eventbus.EventBus

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.settings
 * @ClassName:      SettingsAdapter
 * @Description:    设置页适配器
 * @Author:         Ben
 * @CreateDate:     2020/1/7 18:19
 */
class SettingsAdapter(layoutResId: Int, data: List<SettingItem>) :
    BaseQuickAdapter<SettingItem, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: SettingItem?) {
        helper?.setImageResource(R.id.iv_icon, item!!.RecuseId)
        helper?.setText(R.id.tv_name, item!!.ItemText)

        val switchButton = helper?.getView<SwitchButton>(R.id.sb_item) as SwitchButton
        if (item!!.isShowSwitch) {
            switchButton.visibility = View.VISIBLE
        } else {
            switchButton.visibility = View.GONE
        }

        switchButton.setOnCheckedChangeListener { view, isChecked ->
            //使用EventBus传输状态
         EventBus.getDefault().post(MessageEvent(switchButton.id,isChecked))

        }


    }
}
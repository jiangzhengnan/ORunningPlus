package com.oplayer.orunningplus.function.main.settings

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SettingItem
import com.suke.widget.SwitchButton

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
        if (item!!.isShowSwitch) {
            helper?.setVisible(R.id.sb_item, true)

        } else {
            helper?.setVisible(R.id.iv_arrow, true)
        }


    }
}
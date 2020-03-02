package com.oplayer.orunningplus.function.main.sport

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.SportModelItem

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.sport
 * @ClassName:      SportModeSelectAdapter
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/2/27 10:51
 */
class SportModeSelectAdapter(layoutResId: Int, data: List<SportModelItem>) :
    BaseQuickAdapter<SportModelItem, BaseViewHolder>(layoutResId, data)  {
    override fun convert(helper: BaseViewHolder?, item: SportModelItem?) {

        if (item!!.isSelect) {
            helper?.setImageResource(R.id.iv_sport_type, item.SelectRecuseId)
        }else{
            helper?.setImageResource(R.id.iv_sport_type,item.RecuseId)
        }
        helper?.setText(R.id.tv_sport_type,item.ModelTypeStr)
    }


}
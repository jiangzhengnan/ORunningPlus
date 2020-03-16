package com.oplayer.orunningplus.function.main.sport

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.bean.Sport
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
class SportAdapter(layoutResId: Int, data: List<Sport>) :
    BaseQuickAdapter<Sport, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: Sport?) {

        helper!!.setText(R.id.tv_sport_distace,item!!.distance.toString())



    }




}
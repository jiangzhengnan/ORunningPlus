package com.oplayer.orunningplus.function.test

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.utils.DateUtil
import java.util.*

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.test
 * @ClassName:      MainAdapter
 * @Description:   传入当前时间
 * @Author:         Ben
 * @CreateDate:     2020/1/2 14:35
 */
class CandlerAdapter(layoutResId: Int, data: List<Date>) : BaseQuickAdapter<Date, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: Date) {


        val weekArray = UIUtils.getContext().resources.getStringArray(R.array.candler_week_arr) //already array
        val monthArray = UIUtils.getContext().resources.getStringArray(R.array.candler_month_arr) //already array


        helper?.setText(R.id.tv_day,DateUtil.getDay(item).toString())
        helper?.setText(R.id.tv_week,(weekArray[DateUtil.getWeek(item)-1]))
        helper?.setText(R.id.tv_month,monthArray[DateUtil.getMonth(item)-1])
    }


}
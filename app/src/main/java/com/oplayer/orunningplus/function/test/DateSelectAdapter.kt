package com.oplayer.orunningplus.function.test

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.utils.DateUtil
import java.util.*

/**
 *
 * @ProjectName:    BleProject
 * @Package:        com.oplayer.orunningplus.function.test
 * @ClassName:      DateSelectAdapter
 * @Description:    运动界面时间选择
 * @Author:         Ben
 * @CreateDate:     2020/3/16 11:37
 */
class DateSelectAdapter(layoutResId: Int, data: List<Date>) :
    BaseQuickAdapter<Date, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder?, item: Date?) {


        var monthInt = DateUtil.getMonth(item)
        var monthArr = OSportApplciation.sContext.resources.getStringArray(R.array.candler_month_arr)
        var str = monthArr[monthInt-1] as String

        Slog.d("monthInt   $monthInt")
        Slog.d("str   $str")

        helper!!.setText(R.id.tv_select_date, str)

    }


}
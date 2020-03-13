package com.oplayer.orunningplus.function.connect

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.function.connect.mvp.DeviceDetailData

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.connect.mvp
 * @ClassName:      DeviceDetailAdapter
 * @Description:    产品选择界面二级菜单
 * @Author:         Ben
 * @CreateDate:     2020/3/13 14:13
 */
class DeviceDetailAdapter (layoutResId: Int,data: List<DeviceDetailData>) :
    BaseQuickAdapter<DeviceDetailData, BaseViewHolder>(layoutResId, data){
    override fun convert(helper: BaseViewHolder?, item: DeviceDetailData?) {
        helper!!.setImageResource(R.id.iv_detail,item!!.image)
    }
}
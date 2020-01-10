package com.oplayer.orunningplus.function.main.today.mvp

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.today.mvp
 * @ClassName:      TodayData
 * @Description:    封装首页使用数据
 * @Author:         Ben
 * @CreateDate:     2020/1/9 11:27
 */
class TodayData(type: Int) : MultiItemEntity {


     private var itemType= type




    override fun getItemType(): Int {
    return itemType
    }


}
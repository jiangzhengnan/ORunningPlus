package com.oplayer.orunningplus.bean

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.bean
 * @ClassName:      SportModelItem
 * @Description:    封装多运动模式选择弹框
 * @Author:         Ben
 * @CreateDate:     2020/2/27 10:47
 */
data  class SportModelItem(var isSelect:Boolean,val SelectRecuseId:Int,val RecuseId:Int,val ModelTypeStr:String,val ModelType:Int)
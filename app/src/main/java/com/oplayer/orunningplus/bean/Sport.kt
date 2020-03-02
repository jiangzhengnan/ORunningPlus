package com.oplayer.orunningplus.bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.bean
 * @ClassName:      Sport
 * @Description:    封装运动类存储
 * @Author:         Ben
 * @CreateDate:     2020/2/7 10:27
 *
 * model 运动模式
 * time  开始运动时间
 * sportTime 运动总时长  单位一般为 s
 * step  运动步数
 * calories 运动卡路里
 * distance 运动距离
 * location  运动gps坐标点 格式规则    纬度(latitude),经度(longitude);
 * 34.0177984600,108.9795906400;34.0177984600,108.9795906400;
 *
 * 默认值为-1 显示运动时隐藏该项
 *
 */
open class Sport : RealmObject() {
    @PrimaryKey
    open var id: String? = ""
    open var macAddress: String = ""
    open var model: Int=-1
    open var time: Long=-1
    open var sportTime: Long=-1
    open var step: Int=0
    open var calories: Float=0.0f
    open var distance: Float=0.0f
    open var location: String=""
    open var maxHeart: Int = -1
    open var avgHeart: Int = -1
    open var minHeart: Int = -1
    open var maxFrequency: Int = -1
    open var avgFrequency: Int = -1
    open var minFrequency: Int = -1
    open var maxPace: Int = -1
    open var avgPace: Int = -1
    open var minPace: Int = -1










}

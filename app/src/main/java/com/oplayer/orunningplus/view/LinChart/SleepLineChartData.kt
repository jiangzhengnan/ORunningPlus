package com.oplayer.orunningplus.view.LinChart

/**
 * Created by Tim on 2018/7/6.
 */
/**
 * @author bishiqiangan@yeah.net
 * 数据模型类
 */
class SleepLineChartData(
    //不同睡眠类型的集合
    val dataList: List<SleepData>?,
    // 当前日期的睡眠时间
    val sleepTime: Float,
    //入睡時間
    val sleepLatency: String?
)
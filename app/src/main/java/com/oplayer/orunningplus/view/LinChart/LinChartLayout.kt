package com.oplayer.orunningplus.view.LinChart

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import java.util.*

/**
 * Created by Tim on 2018/7/6.
 */
/**
 * @author bishiqiangan@yeah.net
 * 重写一个LinearLayout， 遍历添加LinChartView
 */
class LinChartLayout : LinearLayout {
    /**
     * 列表的数据源
     */
    private var mData: MutableList<SleepLineChartData>? = null
    /**
     * 屏幕的宽
     */
    private var scrW = 0
    /**
     * 时间类型
     */
    private var timeType = 0

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(context: Context?) : super(context) {
        this.orientation = VERTICAL
        setView()
    }

    fun setView() {
        if (mData != null && mData!!.isNotEmpty()) {
            var textAreW = 20
            if (timeType == TIME_TYPE_WEEK) {
                var text_max_length = 20
                for (data in mData!!) { // 获取最长文字的个数
                    if (!TextUtils.isEmpty(data.sleepLatency)) {
                        if (text_max_length <= data.sleepLatency!!.length) {
                            text_max_length = data.sleepLatency.length
                        }
                    }
                }
                val wh = textWH
                // 文字区域的宽
                textAreW =
                    text_max_length * wh[0] + dip2px(context, 10f)
            }
            // 图形区域的宽
            val chartAreW: Int
            chartAreW = if (textAreW == 20) { //月
                scrW - 200
            } else { //周
                scrW - textAreW - 60
            }
            val layoutParams =
                LayoutParams(
                    scrW - dip2px(
                        context,
                        10f
                    ), dip2px(context, 32f), 1f
                )
            // 设置居中
            layoutParams.gravity = Gravity.CENTER
            // 设置Margin
//            layoutParams.topMargin = dip2px(getContext(), 2);
//            layoutParams.bottomMargin = dip2px(getContext(), 2);
//            layoutParams.leftMargin = dip2px(getContext(), 2);
//            layoutParams.rightMargin = dip2px(getContext(), 2);
            if (mData!!.size > 7) {
                val mMothData: MutableList<SleepLineChartData> =
                    ArrayList()
                mMothData.add(SleepLineChartData(null, 0f, null))
                mMothData.addAll(mData!!)
                mData!!.clear()
                mData!!.addAll(mMothData)
            }
            // 遍历添加LinChartView
            for (i in mData!!.indices) {
                val sleepLineChartData = mData!![i]
                val chartView = LinChartView(context)
                chartView.setData(chartAreW, sleepLineChartData, timeType, i)
                if (i == 0) {
                    if (timeType == TIME_TYPE_MONTH) {
                        val first =
                            LayoutParams(
                                scrW - dip2px(
                                    context,
                                    10f
                                ), dip2px(context, 40f), 1f
                            )
                        // 设置居中
                        first.gravity = Gravity.CENTER
                        this.addView(chartView, first)
                    } else {
                        this.addView(chartView, layoutParams)
                    }
                } else {
                    this.addView(chartView, layoutParams)
                }
            }
        }
    }
    /**
     * 获取单个字符的高和宽
     */
    private val textWH: IntArray
        private get() {
            val wh = IntArray(2)
            // 一个矩形
            val rect = Rect()
            val text = "M"
            val paint = Paint()
            // 设置文字大小
            paint.textSize = dip2px(context, 7f).toFloat()
            paint.getTextBounds(text, 0, text.length, rect)
            wh[0] = rect.width()
            wh[1] = rect.height()
            return wh
        }

    fun setData(
        d: MutableList<SleepLineChartData>?,
        scrw: Int,
        timeType: Int
    ) {
        mData = d
        scrW = scrw
        this.timeType = timeType
        //在添加view之前要把之前所有添加过的view都清除掉
        removeAllViews()
    }

    companion object {
        /**
         * 周
         */
        const val TIME_TYPE_WEEK = 0
        /**
         * 月
         */
        const val TIME_TYPE_MONTH = 1

        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
    }
}
package com.oplayer.orunningplus.view.LinChart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.utils.DateUtil

class LinChartView : View {
    private lateinit var mData: SleepLineChartData 
    private var mChartH = 0f
    private var arcPaint: Paint? = null
    private var timeType = 0
    private var top1 = 0
    private val code = 80
    private val weekStr = intArrayOf(
        R.string.reminders_repeat_sun,
        R.string.reminders_repeat_mon,
        R.string.reminders_repeat_tue,
        R.string.reminders_repeat_wed,
        R.string.reminders_repeat_thu,
        R.string.reminders_repeat_fri,
        R.string.reminders_repeat_sat
    )
    private var position = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画睡眠时间轴
        drawTimeLine(canvas)
        if (timeType == LinChartLayout.TIME_TYPE_MONTH) {
            if (position == 0) {
                return
            }
        }
        drawTextDate(canvas)

        if (!this::mData.isInitialized || mData.dataList==null) {
            return
        }

        top1 = if (timeType == LinChartLayout.TIME_TYPE_WEEK) { // 画文字
            drawText(canvas, mData.sleepTime)
            100
        } else {
            10
        }
        //画图形
        drawLine(canvas)
    }

    private fun drawTimeLine(canvas: Canvas) {
        val timeLinePaint = Paint()
        timeLinePaint.color = resources.getColor(R.color.sleep_details_date)
        timeLinePaint.textSize = dip2px(context, 10f).toFloat()
        // 设置文字右对齐
        timeLinePaint.textAlign = Paint.Align.RIGHT
        val i1 = mChartH / 600 * 120
        val cy = if (timeType == LinChartLayout.TIME_TYPE_MONTH) 50 else 60
        for (i in 0..5) {
            if (position == 0) {
                canvas.drawCircle(i1 * i + code, cy.toFloat(), 10f, timeLinePaint)
                if (i == 0) {
                    canvas.drawText("22 pm", i1 * i + 120, 30f, timeLinePaint)
                } else if (i == 5) {
                    canvas.drawText("8 am", i1 * i + 120, 30f, timeLinePaint)
                }
                canvas.drawLine(
                    i1 * i + code,
                    cy.toFloat(),
                    i1 * i + code,
                    height.toFloat(),
                    timeLinePaint
                )
            } else {
                canvas.drawLine(
                    i1 * i + code,
                    0f,
                    i1 * i + code,
                    height.toFloat(),
                    timeLinePaint
                )
            }
        }
    }

    private fun drawTextDate(canvas: Canvas) {
        val textPaint = Paint()
        //画左边的数据时间
        textPaint.color = resources.getColor(R.color.sleep_details_date)
        textPaint.textSize = dip2px(
            context,
            if (timeType == LinChartLayout.Companion.TIME_TYPE_WEEK) 14f else 12.toFloat()
        ).toFloat()
        // 设置文字左对齐
        textPaint.textAlign = Paint.Align.RIGHT
        val tY: Float
        // 注意第二个参数，左对齐，文字是从右开始写的，那么  x 就是对齐处的X坐标
        if (timeType == LinChartLayout.Companion.TIME_TYPE_WEEK) {
            tY =
                (height - getFontHeight(textPaint)) / 2 + getFontLeading(
                    textPaint
                ) + 50
            canvas.drawText(UIUtils.getString(weekStr[position]), 70f, tY, textPaint)
        } else {
            tY =
                (height - getFontHeight(textPaint)) / 2 + getFontLeading(
                    textPaint
                ) + 5
            if (!TextUtils.isEmpty(mData.sleepLatency)) {
                canvas.drawText(
                    mData.sleepLatency!!.split(" ").toTypedArray()[0].split("-").toTypedArray()[2],
                    70f,
                    tY,
                    textPaint
                )
            } else {
                if (position == 1 || position == 5 || position == 10 || position == 15 || position == 20 || position == 25) {
                    canvas.drawText(position.toString() + "", 70f, tY, textPaint)
                }
            }
        }
    }

    /**
     * 绘制图形
     *
     * @param canvas
     */
    private fun drawLine(canvas: Canvas) {
        arcPaint = Paint()
        var left = code.toFloat()
        val endHour = Integer.valueOf(
            mData.sleepLatency!!.split(" ").toTypedArray()[1].split(":").toTypedArray()[0]
        )
        val date = mData.sleepLatency!!.split(" ").toTypedArray()[0]
        //计算时间差   以00：00为基准
        val value: Float = DateUtil.timeDifference(
            (if (endHour in 21..23) date else DateUtil.getSubtractDay(date)) + " 22:00:00",
            mData.sleepLatency)

        if (value > 0) { //600 是22点到8点的总时长 单位分钟
            val dataWidth = mChartH / 600
            left += value * dataWidth
        }
        val rightMax = mChartH / 600 * 5 * 120 + code
        var right: Float // 循环里面尽量不要创建变量，避免造成频繁的GC
        for (sleepData in mData.dataList!!) {
            when (sleepData.sleepType) {
                SLEEP_TYPE_AWAKE -> arcPaint!!.color = resources.getColor(
                    R.color.sleep_details_awake
                )
                SLEEP_TYPE_LIGHT -> arcPaint!!.color = resources.getColor(
                    R.color.sleep_details_light
                )
                SLEEP_TYPE_DEEP -> arcPaint!!.color = resources.getColor(
                    R.color.sleep_details_deep
                )
            }
            arcPaint!!.isAntiAlias = true // 去除锯齿
            //绘制矩形
            right = left + sleepData.sleepTypeTime  * (mChartH / 600)
            if (left > rightMax) {
                break
            } else if (right > rightMax) {
                canvas.drawRect(left, top1.toFloat(), rightMax, mChartH, arcPaint!!)
//                canvas.drawRoundRect(left, top1.toFloat(), rightMax, mChartH,50f,50f,arcPaint!!)
                break
            }
            canvas.drawRect(left, top1.toFloat(), right, mChartH, arcPaint!!)
//            canvas.drawRoundRect(left, top1.toFloat(), right, mChartH,50f,50f, arcPaint!!)
            left = right
        }
    }

    /**
     * 绘制文字说明  右对齐
     *
     * @param canvas
     * @param sleepTime 睡眠時間
     */
    private fun drawText(canvas: Canvas, sleepTime: Float) {
        val x = width
        val y = height
        val sleepTimeStr: String = "${sleepTime / 60} h ${sleepTime % 60} min"
        val textPaint = Paint()
        //画右边周的数据时间
        textPaint.color = resources.getColor(R.color.sleep_details_goal)
        textPaint.textSize = dip2px(context, 16f).toFloat()
        // 设置文字左对齐
        textPaint.textAlign = Paint.Align.LEFT
        val tX =
            x - getFontLength(textPaint, sleepTimeStr) - 10
        val tY =
            (y - getFontHeight(textPaint)) / 2 + getFontLeading(
                textPaint
            ) + 50
        // 注意第二个参数，左对齐，文字是从右开始写的，那么  x 就是对齐处的X坐标
        canvas.drawText(sleepTimeStr, tX, tY, textPaint)
    }

    /**
     * 设置数据
     *
     * @param chartW
     * @param data
     * @param timeType
     * @param position
     */
    fun setData(chartW: Int, data: SleepLineChartData, timeType: Int, position: Int) {
        mChartH = chartW.toFloat()
        mData = data
        this.timeType = timeType
        this.position = position
        this.postInvalidate()
    }

    companion object {
        const val SLEEP_TYPE_AWAKE = 0 //醒来时间
        const val SLEEP_TYPE_LIGHT = 1 //浅睡时间
        const val SLEEP_TYPE_DEEP = 2 //深睡时间
        /**
         * @return 返回指定笔和指定字符串的长度
         */
        fun getFontLength(paint: Paint, str: String?): Float {
            return paint.measureText(str)
        }

        /**
         * @return 返回指定笔的文字高度
         */
        fun getFontHeight(paint: Paint): Float {
            val fm = paint.fontMetrics
            return fm.descent - fm.ascent
        }

        /**
         * @return 返回指定笔离文字顶部的基准距离
         */
        fun getFontLeading(paint: Paint): Float {
            val fm = paint.fontMetrics
            return fm.leading - fm.ascent
        }

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
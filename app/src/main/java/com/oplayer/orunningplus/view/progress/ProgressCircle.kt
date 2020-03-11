package com.oplayer.orunningplus.view.progress

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.oplayer.orunningplus.R

/**
 * @ProjectName: ORunningPlus
 * @Package: com.oplayer.orunningplus.view.progress
 * @ClassName: ProgressCircle
 * @Description: java类作用描述
 * @Author: Ben
 * @CreateDate: 2020/3/11 10:21
 */
class ProgressCircle : View {
    // ColorInt
    private var startColor = 0

    // ColorInt
    private var endColor = 0

    // ColorInt
    private var defaultColor = 0
    private var percentEndColor = 0
    private var strokeWidth = 0
    private var percent = 0f

    // 用于渐变
    private var paint: Paint? = null
    private var isFootOverHead = false

    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init(context, attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(
        context: Context?,
        attrs: AttributeSet?
    ) {
        var defaultPercent = -1f
        if (isInEditMode) {
            defaultPercent = 0.6f
        }
        do {
            val strokeWdithDefaultValue =
                (18 * resources.displayMetrics.density + 0.5f).toInt()
            if (context == null || attrs == null) {
                strokeWidth = strokeWdithDefaultValue
                percent = defaultPercent
                startColor =
                    resources.getColor(R.color.red_heart_color)
                endColor =
                    resources.getColor(R.color.yellow_heart_color)
                defaultColor =
                    resources.getColor(R.color.green_heart_color)
                break
            }
            var typedArray: TypedArray? = null
            try {
                typedArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.ProgressCircle
                )
                percent = typedArray.getFloat(
                    R.styleable.ProgressCircle_mpc_percent,
                    defaultPercent
                )
                strokeWidth = typedArray.getDimension(
                    R.styleable.ProgressCircle_mpc_stroke_width,
                    strokeWdithDefaultValue.toFloat()
                ).toInt()
                startColor = typedArray.getColor(
                    R.styleable.ProgressCircle_mpc_start_color,
                    resources.getColor(R.color.red_heart_color)
                )
                endColor = typedArray.getColor(
                    R.styleable.ProgressCircle_mpc_end_color,
                    resources.getColor(R.color.yellow_heart_color)
                )
                defaultColor = typedArray.getColor(
                    R.styleable.ProgressCircle_mpc_default_color,
                    resources.getColor(R.color.green_heart_color)
                )
                isFootOverHead = typedArray.getBoolean(
                    R.styleable.ProgressCircle_mpc_foot_over_head,
                    false
                )
            } finally {
                typedArray?.recycle()
            }
        } while (false)
        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.strokeWidth = strokeWidth.toFloat()
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeJoin = Paint.Join.ROUND
        paint!!.strokeCap = Paint.Cap.ROUND
        startPaint = Paint()
        startPaint!!.color = startColor
        startPaint!!.isAntiAlias = true
        startPaint!!.style = Paint.Style.FILL
        endPaint = Paint()
        endPaint!!.isAntiAlias = true
        endPaint!!.style = Paint.Style.FILL
        refreshDelta()
        customColors = intArrayOf(startColor, percentEndColor, defaultColor, defaultColor)
        fullColors = intArrayOf(startColor, endColor)
        emptyColors = intArrayOf(defaultColor, defaultColor)
        customPositions = FloatArray(4)
        customPositions[0] = 0F
        customPositions[3] = 1F
        extremePositions = floatArrayOf(0f, 1f)
    }

    private fun refreshDelta() {
        val endR = endColor and 0xFF0000 shr 16
        val endG = endColor and 0xFF00 shr 8
        val endB = endColor and 0xFF
        startR = startColor and 0xFF0000 shr 16
        startG = startColor and 0xFF00 shr 8
        startB = startColor and 0xFF
        deltaR = endR - startR
        deltaG = endG - startG
        deltaB = endB - startB
    }

    /**
     * @param percent FloatRange(from = 0.0, to = 1.0)
     */
    fun setPercent(percent: Float) {
        var percent = percent
        percent = Math.min(1f, percent)
        percent = Math.max(0f, percent)
        if (this.percent != percent) {
            this.percent = percent
            invalidate()
        }
    }

    fun getPercent(): Float {
        return percent
    }

    /**
     * @param color ColorInt
     */
    fun setStartColor(color: Int) {
        if (startColor != color) {
            startColor = color
            // delta变化
            refreshDelta()

            // 渐变前部分
            customColors[0] = color
            // 前半圆
            startPaint!!.color = color
            // 全满时 渐变起点
            fullColors[0] = color
            invalidate()
        }
    }

    fun getStartColor(): Int {
        return startColor
    }

    /**
     * @param color ColorInt
     */
    fun setEndColor(color: Int) {
        if (endColor != color) {
            endColor = color
            // delta变化
            refreshDelta()

            // 渐变后部分 动态计算#draw
            // 后半圆 需要动态计算#draw，在某些情况下没有

            // 全满时 渐变结束
            fullColors[1] = color
            invalidate()
        }
    }

    fun getEndColor(): Int {
        return endColor
    }

    /**
     * @param color ColorInt
     */
    fun setDefaultColor(color: Int) {
        if (defaultColor != color) {
            defaultColor = color

            // 渐变后半部分
            customColors[2] = color
            customColors[3] = color

            // percent = 0
            emptyColors[0] = color
            emptyColors[1] = color
            invalidate()
        }
    }

    fun getDefaultColor(): Int {
        return defaultColor
    }

    /**
     * @param width px
     */
    fun setStrokeWidth(width: Int) {
        if (strokeWidth != width) {
            strokeWidth = width
            // 画描边的描边变化
            paint!!.strokeWidth = width.toFloat()

            // 会影响measure
            requestLayout()
        }
    }

    fun getStrokeWidth(): Int {
        return strokeWidth
    }

    /**
     * @param footOverHead Boolean
     */
    fun setFootOverHead(footOverHead: Boolean) {
        if (isFootOverHead != footOverHead) {
            isFootOverHead = footOverHead
            invalidate()
        }
    }

    fun isFootOverHead(): Boolean {
        return isFootOverHead
    }

    private var deltaR = 0
    private var deltaB = 0
    private var deltaG = 0
    private var startR = 0
    private var startB = 0
    private var startG = 0
    private fun calculatePercentEndColor(percent: Float) {
        percentEndColor = ((deltaR * percent + startR).toInt() shl 16) +
                ((deltaG * percent + startG).toInt() shl 8) +
                (deltaB * percent + startB).toInt() + -0x1000000
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        rectF.left = measuredWidth / 2 - strokeWidth / 2.toFloat()
        rectF.top = 0f
        rectF.right = measuredWidth / 2 + strokeWidth / 2.toFloat()
        rectF.bottom = strokeWidth.toFloat()
    }

    private var startPaint: Paint? = null
    private var endPaint: Paint? = null
    private val rectF = RectF()
    private lateinit var customColors: IntArray
    private lateinit var fullColors: IntArray
    private lateinit var emptyColors: IntArray
    private lateinit var customPositions: FloatArray
    private lateinit var extremePositions: FloatArray

    // 目前由于SweepGradient赋值只在构造函数，无法pre allocate & reuse instead
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val restore = canvas.save()
        val cx = measuredWidth / 2
        val cy = measuredHeight / 2
        val radius = measuredWidth / 2 - strokeWidth / 2
        var drawPercent = percent
        if (drawPercent > 0.97 && drawPercent < 1) {
            // 设计师说这样比较好
            drawPercent = 0.97f
        }

        // 画渐变圆
        canvas.save()
        canvas.rotate(-90f, cx.toFloat(), cy.toFloat())
        val colors: IntArray
        val positions: FloatArray
        if (drawPercent < 1 && drawPercent > 0) {
            calculatePercentEndColor(drawPercent)
            customColors[1] = percentEndColor
            colors = customColors
            customPositions[1] = drawPercent
            customPositions[2] = drawPercent
            positions = customPositions
        } else if (drawPercent == 1f) {
            percentEndColor = endColor
            colors = fullColors
            positions = extremePositions
        } else {
            // <= 0 || > 1?
            colors = emptyColors
            positions = extremePositions
        }
        val sweepGradient = SweepGradient(
            measuredWidth / 2F,
            measuredHeight / 2F,
            colors,
            positions
        )
        paint!!.shader = sweepGradient
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), paint!!)
        canvas.restore()
        if (drawPercent > 0) {
            // 绘制结束的半圆
            if (drawPercent < 1 || isFootOverHead && drawPercent == 1f) {
                canvas.save()
                endPaint!!.color = percentEndColor
                canvas.rotate(
                    Math.floor(360.0f * drawPercent.toDouble()).toInt() - 1.toFloat(),
                    cx.toFloat(),
                    cy.toFloat()
                )
                canvas.drawArc(rectF, -90f, 180f, true, endPaint!!)
                canvas.restore()
            }
            if (!isFootOverHead || drawPercent < 1) {
                canvas.save()
                // 绘制开始的半圆
                canvas.drawArc(rectF, 90f, 180f, true, startPaint!!)
                canvas.restore()
            }
        }
        canvas.restoreToCount(restore)
    }
}
package com.oplayer.orunningplus.view.progress

import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.oplayer.orunningplus.R

/**
 * @ProjectName: ORunningPlus
 * @Package: com.oplayer.orunningplus.view.progress
 * @ClassName: ProgressBar
 * @Description: java类作用描述
 * @Author: Ben
 * @CreateDate: 2020/3/11 10:21
 */
class ProgressBar : View {
    // ColorInt
    private var fillColor = 0
    private var backgroundColor = 0
    private var fillPaint: Paint? = null
    private var backgroundPaint: Paint? = null
    private var textPaint: Paint? = null
    private var percent = 0f
    private var isFlat = false
    private var isDrawText = true
    private var drawText = "hello,word"

    constructor(context: Context?) : super(context) {
        init(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
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

    private fun init(context: Context?, attrs: AttributeSet?) {
        if (context == null || attrs == null) return;
        var typedArray: TypedArray? = null; try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
            percent = typedArray.getFloat(R.styleable.ProgressBar_pb_percent, 0f);
            fillColor = typedArray.getColor(R.styleable.ProgressBar_pb_fill_color, 0);
            backgroundColor = typedArray.getColor(R.styleable.ProgressBar_pb_background_color, 0);
            isFlat = typedArray.getBoolean(R.styleable.ProgressBar_pb_flat, false)
            isDrawText = typedArray.getBoolean(R.styleable.ProgressBar_pb_show_text, false)
        } finally {
            typedArray?.recycle()
        };
        fillPaint = Paint()
        fillPaint!!.color = fillColor
        fillPaint!!.isAntiAlias = true
        backgroundPaint = Paint()
        backgroundPaint!!.color = backgroundColor
        backgroundPaint!!.isAntiAlias = true

        textPaint = Paint()
        textPaint!!.color = backgroundColor
        textPaint!!.textSize = 30f


    }

    /**
     * @param fillColor ColorInt
     */
    fun setFillColor(fillColor: Int) {
        if (this.fillColor != fillColor) {
            this.fillColor = fillColor
            fillPaint!!.color = fillColor
            invalidate()
        }
    }

    /**
     * @param backgroundColor ColorInt
     */
    override fun setBackgroundColor(backgroundColor: Int) {
        if (this.backgroundColor != backgroundColor) {
            this.backgroundColor = backgroundColor
            backgroundPaint!!.color = backgroundColor
            invalidate()
        }
    }

    fun getFillColor(): Int {
        return fillColor
    }

    fun getBackgroundColor(): Int {
        return backgroundColor
    }

    fun getPercent(): Float {
        return percent
    }

    /**
     * @param percent FloatRange(from = 0.0, to = 1.0)
     */
    fun setPercent(percent: Float) {
        val valueAnimator = ValueAnimator.ofFloat(0F, percent)
        valueAnimator.duration = 5000
        valueAnimator.addUpdateListener { animation ->
            val animatedValue = animation?.animatedValue as Float
            var value = animatedValue
            value = Math.min(1f, value)
            value = Math.max(0f, value)
            if (this.percent != value) {
                this.percent = value
                invalidate()
            }

        }
        valueAnimator.start()


    }


    fun isShowText(isShow:Boolean){
        isDrawText=isShow

    }


    fun setPercent(percent: Float,text:String) {
        val valueAnimator = ValueAnimator.ofFloat(0F, percent)
        valueAnimator.duration = 5000
        drawText=text
        valueAnimator.addUpdateListener { animation ->
            val animatedValue = animation?.animatedValue as Float
            var value = animatedValue
            if (this.percent != value) {
                this.percent = value
                invalidate()
            }

        }
        valueAnimator.start()


    }



    /**
     * @param flat Whether the right side of progress is round or flat
     */
    fun setFlat(flat: Boolean) {
        if (isFlat != flat) {
            isFlat = flat
            invalidate()
        }
    }

    private val rectF = RectF()

    //    private final Path regionPath = new Path();
    //    private final Path fillPath = new Path();
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val drawPercent = percent
        canvas.save()
        val height = measuredHeight - paddingTop - paddingBottom
        val width = measuredWidth - paddingLeft - paddingRight
        val fillWidth = drawPercent * width
        val radius = height / 2.0f
        rectF.left = 0f
        rectF.top = 0f
        rectF.right = width.toFloat()
        rectF.bottom = height.toFloat()
        if (backgroundColor != 0) {
            canvas.drawRoundRect(rectF, radius, radius, backgroundPaint!!)
        }



        try {
            if (fillColor != 0 && fillWidth > 0) {
                if (fillWidth == width.toFloat()) {
                    rectF.right = fillWidth
                    canvas.drawRoundRect(rectF, radius, radius, fillPaint!!)
                    if (isDrawText) {
                        canvas.drawText(drawText, radius, radius + 10, textPaint!!)
                    }
                    return
                }
                if (isFlat) {
                    canvas.save()
                    rectF.right = if (fillWidth > radius) radius else fillWidth
                    canvas.clipRect(rectF)
                    rectF.right = radius * 2
                    canvas.drawRoundRect(rectF, radius, radius, fillPaint!!)
                    canvas.restore()
                    if (fillWidth <= radius) {
                        return
                    }
                    val leftAreaWidth = width - radius
                    val centerX =
                        if (fillWidth > leftAreaWidth) leftAreaWidth else fillWidth
                    rectF.left = radius
                    rectF.right = centerX
                    canvas.drawRect(rectF, fillPaint!!)
                    if (fillWidth <= leftAreaWidth) {
                        return
                    }
                    rectF.left = leftAreaWidth - radius
                    rectF.right = fillWidth
                    canvas.clipRect(rectF)
                    rectF.right = width.toFloat()
                    canvas.drawArc(rectF, -90f, 180f, true, fillPaint!!)
                } else {

                    if (fillWidth <= radius * 2) {
                        rectF.right = fillWidth
                        canvas.clipRect(rectF)
                        rectF.right = radius * 2
                        canvas.drawRoundRect(rectF, radius, radius, fillPaint!!)
                    } else {
                        rectF.right = fillWidth
                        canvas.drawRoundRect(rectF, radius, radius, fillPaint!!)
                    }


                }


            }


        } finally {
            canvas.restore()
        }
//文字绘制
        if (isDrawText) {
            canvas.drawText(drawText, radius, radius + 10, textPaint!!)
        }

    }


}
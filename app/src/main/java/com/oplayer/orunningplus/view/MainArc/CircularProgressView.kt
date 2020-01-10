package com.oplayer.orunningplus.view.MainArc

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.common.utils.UIUtils.Companion.getColor
import com.oplayer.orunningplus.R

/**
 * @ProjectName: ORunningPlus
 * @Package: com.oplayer.orunningplus.view.MainArc
 * @ClassName: CircularProgressView
 * @Description: java类作用描述
 * @Author: Ben
 * @CreateDate: 2020/1/7 10:33
 */
class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mBackPaint: Paint
    private val mProgPaint: Paint
//    private val mTextPaint // 绘制画笔
//            : Paint
    private var mRectF // 绘制区域
            : RectF? = null
    private var mColorArray // 圆环渐变色
            : IntArray?
    private var mProgress // 圆环进度(0-100)
            : Int
    private var viewWide = 0
    private var viewHigh = 0
    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWide = measuredWidth - paddingLeft - paddingRight
        viewHigh = measuredHeight - paddingTop - paddingBottom
        val mRectLength =
            ((if (viewWide > viewHigh) viewHigh else viewWide) - if (mBackPaint.strokeWidth > mProgPaint.strokeWidth) mBackPaint.strokeWidth else mProgPaint.strokeWidth).toInt()
        val mRectL = paddingLeft + (viewWide - mRectLength) / 2
        val mRectT = paddingTop + (viewHigh - mRectLength) / 2


     Slog.d("CircularProgressView  viewWide $viewWide   viewHigh $viewHigh   mRectL $mRectL   mRectT  $mRectT")


        mRectF = RectF(mRectL.toFloat(), mRectT.toFloat(), (mRectL + mRectLength).toFloat(), (mRectT + mRectLength).toFloat())
        // 设置进度圆环渐变色
        if (mColorArray != null && mColorArray!!.size > 1) mProgPaint.shader = LinearGradient(0F, 0F, 0F, measuredWidth.toFloat(), mColorArray as IntArray, null, Shader.TileMode.MIRROR)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(mRectF!!, 0f, 360f, false, mBackPaint)
        canvas.drawArc(mRectF!!, 275f, 360 * mProgress / 100.toFloat(), false, mProgPaint)
    }
    // ---------------------------------------------------------------------------------------------
    /**
     * 获取当前进度
     *
     * @return 当前进度（0-100）
     */
    /**
     * 设置当前进度
     *
     * @param progress 当前进度（0-100）
     */
    var progress: Int
        get() = mProgress
        set(progress) {
            mProgress = progress
            invalidate()
        }

    /**
     * 设置当前进度，并展示进度动画。如果动画时间小于等于0，则不展示动画
     *
     * @param progress 当前进度（0-100）
     * @param animTime 动画时间（毫秒）
     */
    fun setProgress(progress: Int, animTime: Long) {
        if (animTime <= 0) this.progress = progress else {
            val animator = ValueAnimator.ofInt(mProgress, progress)
            animator.addUpdateListener { animation -> mProgress = animation.animatedValue as Int; invalidate() }
            animator.interpolator = OvershootInterpolator()
            animator.duration = animTime
            animator.start()
        }
    }

    /**
     * 设置背景圆环宽度
     *
     * @param width 背景圆环宽度
     */
    fun setBackWidth(width: Int) {
        mBackPaint.strokeWidth = width.toFloat()
        invalidate()
    }

    /**
     * 设置背景圆环颜色
     *
     * @param color 背景圆环颜色
     */
    fun setBackColor(@ColorRes color: Int) {
        mBackPaint.color = ContextCompat.getColor(context, color)
        invalidate()
    }

    /**
     * 设置进度圆环宽度
     *
     * @param width 进度圆环宽度
     */
    fun setProgWidth(width: Int) {
        mProgPaint.strokeWidth = width.toFloat()
        invalidate()
    }

    /**
     * 设置进度圆环颜色
     *
     * @param color 景圆环颜色
     */
    fun setProgColor(@ColorRes color: Int) {
        mProgPaint.color = ContextCompat.getColor(context, color)
        mProgPaint.shader = null
        invalidate()
    }

    /**
     * 设置进度圆环颜色(支持渐变色)
     *
     * @param startColor 进度圆环开始颜色
     * @param firstColor 进度圆环结束颜色
     */
    fun setProgColor(@ColorRes startColor: Int, @ColorRes firstColor: Int) {
        mColorArray = intArrayOf(
            ContextCompat.getColor(context, startColor),
            ContextCompat.getColor(context, firstColor)
        )
        mProgPaint.shader = LinearGradient(
            0F,
            0F,
            0F,
            measuredWidth.toFloat(),
            mColorArray as IntArray ,
            null,
            Shader.TileMode.MIRROR
        )
        invalidate()
    }

    /**
     * 设置进度圆环颜色(支持渐变色)
     *
     * @param colorArray 渐变色集合
     */
    fun setProgColor(@ColorRes colorArray: IntArray?) {
        if (colorArray == null || colorArray.size < 2) return
        mColorArray = IntArray(colorArray.size)
        for (index in colorArray.indices) mColorArray!![index] =
            ContextCompat.getColor(context, colorArray[index])
        mProgPaint.shader = LinearGradient(
            0F,
            0F,
            0F,
            measuredWidth.toFloat(),
            mColorArray as IntArray ,
            null,
            Shader.TileMode.MIRROR
        )
        invalidate()
    }

    init {
        @SuppressLint("Recycle") val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView)
        // 初始化背景圆环画笔
        mBackPaint = Paint()
        mBackPaint.style = Paint.Style.STROKE // 只描边，不填充
        mBackPaint.strokeCap = Paint.Cap.ROUND // 设置圆角
        mBackPaint.isAntiAlias = true // 设置抗锯齿
        mBackPaint.isDither = true // 设置抖动
        mBackPaint.strokeWidth = typedArray.getDimension(
            R.styleable.CircularProgressView_backWidth,
            30f
        )
        mBackPaint.color = typedArray.getColor(
            R.styleable.CircularProgressView_backColor,
            Color.LTGRAY
        )
        // 初始化进度圆环画笔
        mProgPaint = Paint()
        mProgPaint.style = Paint.Style.STROKE // 只描边，不填充
        mProgPaint.strokeCap = Paint.Cap.ROUND // 设置圆角
        mProgPaint.isAntiAlias = true // 设置抗锯齿
        mProgPaint.isDither = false // 设置抖动
        mProgPaint.strokeWidth = typedArray.getDimension(R.styleable.CircularProgressView_progWidth, 30f)
        mProgPaint.color = typedArray.getColor(R.styleable.CircularProgressView_progColor, getColor(R.color.yellow_progress_color))

        // 初始化文字画笔
//        mTextPaint = Paint()
//        mTextPaint.isAntiAlias = true // 设置抗锯齿
//        mTextPaint.isDither = true // 设置抖动
//        mTextPaint.textSize=30F
//        mTextPaint.style=Paint.Style.STROKE
//        mTextPaint.textAlign = Paint.Align.CENTER
//        mTextPaint.color = getColor(R.color.white_date_text_color)
        //  gray_date_text_color  灰色
// 初始化进度圆环渐变色
        val startColor =
            typedArray.getColor(R.styleable.CircularProgressView_progStartColor, -1)
        val firstColor =
            typedArray.getColor(R.styleable.CircularProgressView_progFirstColor, -1)
        mColorArray =
            if (startColor != -1 && firstColor != -1) intArrayOf(startColor, firstColor) else null
        // 初始化进度
        mProgress = typedArray.getInteger(R.styleable.CircularProgressView_progress, 0)
        typedArray.recycle()
    }
}
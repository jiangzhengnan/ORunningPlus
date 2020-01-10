package com.oplayer.orunningplus.view.MainArc

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
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
class HeartProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mProgPaint: Paint
    private val mTrianglePaint: Paint// 三角画笔
    //            : Paint
    private var mRectF // 绘制区域
            : RectF? = null
    private var mProgress
            : Int
    private var viewWide = 0
    private var viewHigh = 0

//    var centerX = 0
//    var centerY = 0
//    var arcWidth = 0
//    var w_r = 0

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWide = measuredWidth - paddingLeft - paddingRight
        viewHigh = measuredHeight - paddingTop - paddingBottom


//        centerX = viewWide / 2//中心点x坐标
//        centerY = viewHigh//中心点y坐标
//        arcWidth = viewHigh / 2//圆弧宽带
//        w_r = viewHigh - arcWidth //外圈半径 = 高度 - 圆弧宽度 - 20（去掉一点，一面刚好顶大view的边上）

        val mRectLength = (if (viewWide > viewHigh) viewHigh else viewWide)-mProgPaint.strokeWidth


        val mRectL = paddingLeft + (viewWide - mRectLength) / 2
        val mRectT = paddingTop + (viewHigh - mRectLength) / 2
        mRectF = RectF(
            mRectL.toFloat(),
            mRectT.toFloat(),
            (mRectL + mRectLength).toFloat(),
            (mRectT + mRectLength).toFloat()
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mProgPaint.setColor(getColor(R.color.red_heart_color))
        canvas.drawArc(mRectF!!, 5f, 50f, false, mProgPaint)
        mProgPaint.setColor(getColor(R.color.gray_heart_color))
        canvas.drawArc(mRectF!!, 125f, 50f, false, mProgPaint)
        mProgPaint.setColor(getColor(R.color.blue_heart_color))
        canvas.drawArc(mRectF!!, 185f, 50f, false, mProgPaint)
        mProgPaint.setColor(getColor(R.color.green_heart_color))
        canvas.drawArc(mRectF!!, 245f, 50f, false, mProgPaint)
        mProgPaint.setColor(getColor(R.color.yellow_heart_color))
        canvas.drawArc(mRectF!!, 305f, 50f, false, mProgPaint)

//        dgree_total = progress * (180f / maxProgress);//计算出当前刻度值对应应该转动的角度

//        var dgree_total = 50 * (290 / 100)
//
//        var x = 0.0f //某个进度值对应的外圆弧上的点的x坐标
//
//        var y = 0.0f //某个进度值对应的外圆弧上的点的y坐标

//        val count = (dgree_total % 90 / 18) as Int


//        if (dgree_total > 90) {//某个值对应的角度大于90度的时候，计算对应的x与y的所对应的三角函数不同，所以以90度作为界点分别计算
//            var md = 180 - dgree_total
//            x = (centerX + (w_r + arcWidth) * Math.cos(Math.PI * md / 180.0f)).toFloat()
//            y = (centerY - (w_r + arcWidth) * Math.sin(Math.PI * md / 180.0f)).toFloat()
//        } else {
//            x = (centerX - (w_r + arcWidth) * Math.cos(Math.PI * dgree_total / 180.0f)).toFloat()
//            y = (centerY - (w_r + arcWidth) * Math.sin(Math.PI * dgree_total / 180.0f)).toFloat()
//        }
        //实例化路径 放大三角形
//        var path = Path()
//        path.moveTo(x, y)
//        path.lineTo(x-30, y)
//        path.lineTo(x+30, y)
//        path.close() // 使这些点构成封闭的多边形
//        canvas.drawPath(path, mTrianglePaint)


    }

    fun getY(y: Float): Float {
        return 360 * y / 100.toFloat()
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
            animator.addUpdateListener { animation ->
                mProgress = animation.animatedValue as Int; invalidate()
            }
            animator.interpolator = OvershootInterpolator()
            animator.duration = animTime
            animator.start()
        }
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


    init {
        @SuppressLint("Recycle") val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView)

        //初始化三角画笔
        mTrianglePaint = Paint()
        mTrianglePaint.setAntiAlias(true)
        mTrianglePaint.setColor(getColor(R.color.icon_green_color))
        mTrianglePaint.setStyle(Paint.Style.FILL)
        mTrianglePaint.setStrokeWidth(3f)

        mProgPaint = Paint()
        mProgPaint.style = Paint.Style.STROKE // 只描边，不填充
        mProgPaint.strokeCap = Paint.Cap.ROUND // 设置圆角
        mProgPaint.isAntiAlias = true // 设置抗锯齿
        mProgPaint.isDither = false // 设置抖动
        mProgPaint.strokeWidth =
            typedArray.getDimension(R.styleable.CircularProgressView_progWidth, 25f)
        mProgPaint.color = typedArray.getColor(
            R.styleable.CircularProgressView_progColor,
            getColor(R.color.yellow_progress_color)
        )
        // 初始化进度
        mProgress = typedArray.getInteger(R.styleable.CircularProgressView_progress, 0)
        typedArray.recycle()
    }
}
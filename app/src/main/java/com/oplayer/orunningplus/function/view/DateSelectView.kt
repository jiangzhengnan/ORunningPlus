package com.oplayer.orunningplus.function.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.utils.DateUtil
import kotlinx.android.synthetic.main.view_date_switch.view.*
import java.util.*


/**
 *
 * @ProjectName:    BleProject
 * @Package:        com.oplayer.orunningplus.function.view
 * @ClassName:      DateSelectView
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/2/14 14:26
 */
class DateSelectView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var weekArray: Array<String>
    var monthArray: Array<String>

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DateSelectView)
        weekArray = context.resources.getStringArray(R.array.date_week_arr)
        monthArray = context.resources.getStringArray(R.array.date_month_array)
        initView(typedArray)
        typedArray.recycle()
    }

    private fun initView(typedArray: TypedArray) {
        var view = View.inflate(context, R.layout.view_date_switch, this)
        var timeModel = typedArray.getInt(R.styleable.DateSelectView_timeModel, 0)
        /**
         * timeModel
         * 0 当前显示日期为日
         * 1 当前显示日期格式为星期
         * 2 当前显示日期格式为月
         * */
        when (timeModel) {
            0 -> initDayView()
            1 -> initWeekView()
            2 -> initMonthView()
        }


    }

    private fun initMonthView() {
        var currDate = Date()
        setMonthTime(currDate)
        img_date_previous.setOnClickListener {
            currDate= DateUtil.getBeforeMonth(currDate)!!
            setMonthTime(currDate)
        }
        img_date_next.setOnClickListener {
            currDate= DateUtil.getNextMonth(currDate)!!
            setMonthTime(currDate)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setMonthTime(currDate: Date) {
        listener?.OnChage(currDate)
        val week = DateUtil.getWeek(currDate)
        val day = DateUtil.getDay(currDate)
        val month = DateUtil.getMonth(currDate)
        val year = DateUtil.getYear(currDate)
        var array=  context.resources.getStringArray(R.array.top_bar_arr)
        tv_date_switch_today.text =array[2]
        tv_date_switch_time.text = "${monthArray[month-1]}   $year"
    }

    private fun initWeekView() {
        var currDate = Date()
        setWeekTime(currDate)
        img_date_previous.setOnClickListener {
            currDate= DateUtil.getPreviousWeek(currDate)
            setWeekTime(currDate)
        }
        img_date_next.setOnClickListener {
            currDate= DateUtil.getNextWeek(currDate)
            setWeekTime(currDate)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setWeekTime(currDate: Date) {
        listener?.OnChage(currDate)
   val weekStart=     DateUtil.getWeekStart(currDate)
        val weekEnd=      DateUtil.getWeekEnd(currDate)
        val startDay = DateUtil.getDay(weekStart)+1
        val endDay = DateUtil.getDay(weekEnd)+1
        val month = DateUtil.getMonth(currDate)
        val year = DateUtil.getYear(currDate)
        var array=  context.resources.getStringArray(R.array.top_bar_arr)
        tv_date_switch_today.text =array[1]
        tv_date_switch_time.text = "$startDay-$endDay ${monthArray[month-1]} $year"



    }

    @SuppressLint("SetTextI18n")
    private fun initDayView() {
        //默认显示当前日期
        var currDate = Date()

        setDayTime(currDate)

        img_date_previous.setOnClickListener {
            //显示前一天日期
            currDate= DateUtil.getBeforeDay(currDate)!!
            setDayTime(currDate)
        }
        img_date_next.setOnClickListener {
            //显示后一天日期
            currDate= DateUtil.getNextDay(currDate)!!
            setDayTime(currDate)
        }


    }

    @SuppressLint("SetTextI18n")
    private fun setDayTime(currDate: Date) {
        listener?.OnChage(currDate)
        val week = DateUtil.getWeek(currDate)
        val day = DateUtil.getDay(currDate)
        val month = DateUtil.getMonth(currDate)
        val year = DateUtil.getYear(currDate)
        tv_date_switch_today.text = weekArray[week-1]
        tv_date_switch_time.text = " $day   ${monthArray[month-1]}   $year"
    }


    interface onDateChageListener {
        fun OnChage(date: Date)
    }
    private var listener: onDateChageListener? = null

    fun setListener(listener: onDateChageListener?) {
        this.listener = listener
    }

}


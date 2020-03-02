package com.oplayer.orunningplus.function.calender

import android.content.Intent
import android.view.View
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.function.main.today.TodayFragment
import com.oplayer.orunningplus.utils.DateUtil
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.activity_calender.*
import java.time.LocalDate
import java.util.*

class CalenderActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_calender
    }

    override fun initData() {
    }

    override fun initView() {

        initToolbar(UIUtils.getString(R.string.profile_date_pick), true)
        val selectDate = intent.getStringExtra(TodayFragment.SELECT_DATE)
        showCandler(selectDate)

    }

    override fun initInfo() {

    }

    override fun onClick(v: View) {

    }

    override fun onGetEvent(event: MessageEvent) {

    }

    private fun showCandler(selectDate: String) {
        var currDateArray = DateUtil.getCurDateStr()!!.split("-").map { it.toInt() }
        val today = CalendarDay.from(currDateArray[0], currDateArray[1], currDateArray[2])
        calendarView.currentDate = today
        var dateArray = selectDate.split("-").map { it.toInt() }
        val selectDate = CalendarDay.from(dateArray[0], dateArray[1], dateArray[2])
        //当前选中的日期
        calendarView.selectedDate = selectDate

        calendarView.titleAnimationOrientation = MaterialCalendarView.VERTICAL
        calendarView.state().edit()
//            .setFirstDayOfWeek(DayOfWeek.WEDNESDAY)
            .setMinimumDate(CalendarDay.from(2019, 1, 1))
            .setMaximumDate(today)
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        calendarView.setOnDateChangedListener { widget, date, selected ->
            Slog.d("日期选择 date ${date.day}  seleced $selected    widget $widget  ")
            val intent = Intent()
            intent.putExtra(TodayFragment.SELECT_DATE, "${date.year}-${date.month}-${date.day}")
            setResult(TodayFragment.resultInt, intent)
            finish()
        }


    }

}

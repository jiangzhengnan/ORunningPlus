package com.oplayer.orunningplus.function.test

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.base.BaseActivity
import com.oplayer.orunningplus.event.MessageEvent
import com.oplayer.orunningplus.utils.DateUtil
import java.util.*


class Test2Activity : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)





        val endDate=Date()
        val endCalendar = Calendar.getInstance()//得到calendar
        endCalendar.time=endDate
        val startCalendar = Calendar.getInstance()//得到calendar
        startCalendar.time = endDate//当前时间设置给calendar
        startCalendar.add(Calendar.YEAR, -130)  //当前时间的前6个月


       var selectDate= DateUtil.str2Date("1997-02-22","yyyy-MM-dd")


        val selectCalendar = Calendar.getInstance()
        selectCalendar.time = selectDate
        val pvTime =
            TimePickerBuilder(this@Test2Activity,
                OnTimeSelectListener { date, v ->
                    Toast.makeText(this@Test2Activity, date.toString(), Toast.LENGTH_SHORT)
                        .show()
                })
//                .setType()// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("Sure")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("日期选择")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(getIconColor())//标题文字颜色
                .setSubmitColor(getIconColor())//确定按钮文字颜色
                .setCancelColor(getIconColor())//取消按钮文字颜色
                .setTitleBgColor(getBGColor())//标题背景颜色 Night mode
                .setBgColor(getBGColor())//滚轮背景颜色 Night mode
                .setDate(selectCalendar)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startCalendar,endCalendar)//起始终止年月日设定
//                .setLabel("Year","Month","Day","Hour","Min","Sec")//默认设置为年月日时分秒
                .setLabel("","","","","","")//默认设置为年月日时分秒

                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build()


        pvTime.show()


    }

    override fun getLayoutId(): Int {
        return  R.layout.activity_test2
    }

    override fun initData() {
        
    }

    override fun initView() {
        
    }

    override fun initInfo() {
        
    }

    override fun onClick(v: View) {
        
    }

    override fun onGetEvent(event: MessageEvent) {
        
    }


}


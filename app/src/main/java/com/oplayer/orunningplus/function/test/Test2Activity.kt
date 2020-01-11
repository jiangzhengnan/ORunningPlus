package com.oplayer.orunningplus.function.test

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.utils.javautils.JavaUtil
import com.oplayer.orunningplus.view.DiscreteScroll.DSVOrientation
import kotlinx.android.synthetic.main.fragment_today.*
import java.util.*


class Test2Activity : AppCompatActivity() {

    val dayMin = 1440

    var mCurrPosition: Int = 0
    var dateList = mutableListOf<Date>()
    init {
        initDateList()
        mCurrPosition = dateList.size - 90
    }



    /**
     * 构建首页日历
     * */

    private fun initDateList(): List<Date> {
        var tmpTime: Long = System.currentTimeMillis()
        var leftTime: Long = System.currentTimeMillis()
        for (index in 0..90) { dateList.add(Date(tmpTime)); tmpTime += 1000 * 60 * 60 * 24 }
        for (index in 0..90) { leftTime -= 1000 * 60 * 60 * 24; dateList.add(0, Date(leftTime)) }
        return dateList
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

//        var candlerAdapter = CandlerAdapter(R.layout.item_today_candler, dateList)
//        dsv_candler.setOverScrollEnabled(true)
//        dsv_candler.setSlideOnFling(false)
//        dsv_candler.setOffscreenItems(7)
//        dsv_candler.setOrientation(DSVOrientation.HORIZONTAL)
//        dsv_candler.setOnScrollListener(mOnScrollListener)
//        dsv_candler.adapter = candlerAdapter
//        dsv_candler.scrollToPosition(mCurrPosition)
//
//        dsv_candler.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//
//               var diff = JavaUtil.getAbs(  dsv_candler.currentItem - mCurrPosition)
//            Slog.d("绝对差值   $diff")
//                   //中点
//            if (diff > 2) {
//                view_today.visibility = View.VISIBLE
//            }else{
//                view_today.visibility = View.GONE
//            }
//
//
//
//        }
//
//        view_today.visibility = View.VISIBLE
//        view_today.setOnClickListener {
//
//            dsv_candler.smoothScrollToPosition(mCurrPosition)
//
//        }
    }


    private val mOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val childCount = recyclerView.childCount//总item的数量
                val width = recyclerView.getChildAt(0).width//第一个item的宽度
                val padding =
                    (recyclerView.width - width) / 2//这个padding是 recycler的宽度减去第一个item的宽度然后除以2，作为padding
                for (index in 0..childCount) {
                    val v = recyclerView.getChildAt(index) ?: return//获取每一个child
                    var rate = 0f//是一个缩放比例
                    if (v.left <= padding) {//如果view距离左边的宽度 小于等于 左侧剩余空间(padding) （意味着这个view开始往左边滑动了，并且有遮挡）
                        if (v.left >= padding - v.width) {//如果view距离左边的距离 小于等于滑进去的距离 （其实就是说滑动到一半的时候）
                            v.findViewById<TextView>(R.id.tv_month)
                                .setTextColor(UIUtils.getColor(R.color.icon_green_color))
                            v.findViewById<TextView>(R.id.tv_day)
                                .setTextColor(UIUtils.getColor(R.color.icon_green_color))
                            v.findViewById<TextView>(R.id.tv_week)
                                .setTextColor(UIUtils.getColor(R.color.icon_green_color))
                            rate =
                                (padding - v.left) * 1.1f / v.width//（这个比例的计算结果一般都会大于1，这样一来，根据下面的 1- rate * 0.1 得出，这个比例最多不会到达1，也就是 1- 0.1， 也就是 0.9， 所以这个view的宽度最大不会小于他本身的90%）
                        } else {
                            rate = 1f
                            v.findViewById<TextView>(R.id.tv_month)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                            v.findViewById<TextView>(R.id.tv_day)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                            v.findViewById<TextView>(R.id.tv_week)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                        }

                        v.scaleY = 1 - rate * 0.1f
                    } else {
                        if (v.left <= recyclerView.width - padding) {//这个过程大概是指这个view 从最后侧刚刚出现的时候开始滑动过padding的距离
                            rate =
                                (recyclerView.width - padding - v.left) * 1f / v.width
                            v.findViewById<TextView>(R.id.tv_month)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                            v.findViewById<TextView>(R.id.tv_day)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))
                            v.findViewById<TextView>(R.id.tv_week)
                                .setTextColor(UIUtils.getColor(R.color.white_date_text_color))

                        }
                        v.scaleY = 0.9f + rate * 0.1f
                    }


                }


            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
//                    smoothMoveToPosition(dsv_candler, mToPosition)
//                    mShouldScroll = false
//                    Slog.d("需要重新确认位置   $mShouldScroll")
//
//
//                }

            }

        }


}


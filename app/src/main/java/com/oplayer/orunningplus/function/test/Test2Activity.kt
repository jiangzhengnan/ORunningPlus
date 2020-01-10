package com.oplayer.orunningplus.function.test

import android.icu.util.UniversalTimeScale.MAX_SCALE
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.R
import kotlinx.android.synthetic.main.activity_test2.*
import java.util.*


class Test2Activity : AppCompatActivity() {

    val dayMin = 1440
    var dateList = mutableListOf<Date>()

    init {
        initDateList()
    }

    private fun initDateList(): List<Date> {

        var mCurrentTime = System.currentTimeMillis()
//        var mCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
//        mCalendar.setTime(Date(mCurrentTime))
//        val index: Int = mCalendar.get(Calendar.DAY_OF_WEEK)

        var tmpTime: Long = mCurrentTime
        var leftTime: Long = mCurrentTime

        for (index in 0..3) {
            dateList.add(Date(tmpTime))
            tmpTime += 1000 * 60 * 60 * 24
        }
        for (index in 0..30) {
            leftTime -= 1000 * 60 * 60 * 24
            dateList.add(0, Date(leftTime))
        }

        return dateList
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
//        dsv_candler.setOrientation(DSVOrientation.HORIZONTAL)
        var candlerAdapter = CandlerAdapter(R.layout.item_today_candler, dateList)
//        dsv_candler.setItemTransitionTimeMillis(100)
//        dsv_candler.setOverScrollEnabled(true)
//        dsv_candler.setSlideOnFling(true)
//        dsv_candler.setOffscreenItems(7)
        var  linearLayoutManager=  LinearLayoutManager(this)
        linearLayoutManager.orientation=LinearLayoutManager.HORIZONTAL
        dsv_candler.layoutManager=linearLayoutManager


        dsv_candler.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (oldScrollX < 0) {
                Slog.d("》》》")
            } else {
                Slog.d("《《《")
            }


        }



        Slog.d("scrollToPosition   ${dateList.size - 3}")
        dsv_candler.scrollToPosition(dateList.size-3)
//        dsv_candler.addItemDecoration(SpaceItemDecoration(0, 1))
//        dsv_candler.setItemTransformer(
//            ScaleTransformer.Builder()
//                .setMinScale(0.8f)
//                .build()
//        )
        dsv_candler.adapter = candlerAdapter

    }


    private val mOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val childCount = recyclerView.childCount
                for (i in 0 until childCount) {
                    val child = recyclerView.getChildAt(i) as LinearLayout

                    val lp =
                        child.layoutParams as LinearLayout.LayoutParams
                    child.layoutParams=lp



                }
            }
        }

}

package com.oplayer.orunningplus.function.main.today.mvp

import android.animation.ValueAnimator
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.oplayer.common.common.SleepDateType
import com.oplayer.common.common.TodayDateType
import com.oplayer.common.utils.UIUtils.Companion.getColor
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.view.MainArc.CircularProgressView
import kotlinx.android.synthetic.main.activity_test2.*

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.function.main.today.mvp
 * @ClassName:      TodayAdapter
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/1/9 11:33
 */
class TodayAdapter(data: List<TodayData>) :
    BaseMultiItemQuickAdapter<TodayData, BaseViewHolder>(data) {

    val commDuration=1500L

    init {
        setDefaultViewTypeLayout(R.layout.item_today_step)
        addItemType(TodayDateType.STEP, R.layout.item_today_step)
        addItemType(TodayDateType.HEART, R.layout.item_today_heart)
        addItemType(TodayDateType.SLEEP, R.layout.item_today_sleep)
        addItemType(TodayDateType.SPORT, R.layout.item_today_sport)
    }


    override fun convert(helper: BaseViewHolder?, item: TodayData?) {

        when (helper?.itemViewType) {
            TodayDateType.STEP -> {
                /**初始化多布局*/

                val animation=     ValueAnimator.ofInt(6500).setDuration(commDuration)
                animation  .addUpdateListener {

                    helper.setText(R.id.tv_step,it.animatedValue.toString())


                }
                animation.start()
                var apStep = helper.getView<CircularProgressView>(R.id.ap_step)
                apStep.setProgress(6500/8000*100,commDuration)



            }
            TodayDateType.HEART -> {
                /**初始化多布局*/
                val animation=     ValueAnimator.ofInt(105).setDuration(commDuration)
                animation  .addUpdateListener { helper.setText(R.id.tv_heart,it.animatedValue.toString()) }
                animation.start()
            }
            TodayDateType.SLEEP -> {
                /**初始化多布局*/
                var sleepChart = helper.getView<PieChart>(R.id.sleep_pic_chart)

                drawChart(sleepChart)


            }
            TodayDateType.SPORT -> {
                /**初始化多布局*/
                /**初始化多布局*/
                val animation=     ValueAnimator.ofFloat(15.5f).setDuration(commDuration)
                animation  .addUpdateListener { helper.setText(R.id.tv_sport_distace, String.format("%.2f", it.animatedValue))}
                animation.start()
            }

        }


    }

    private fun drawChart(sleepChart: PieChart) {
        val strings: MutableList<PieEntry> = ArrayList()
        //将睡眠数据转换为分钟  按顺序绘制，绘制昨天数据时 先用灰色填充
        strings.add(PieEntry(getMIn(60f), ""))
        strings.add(PieEntry(getMIn(45f), ""))
        strings.add(PieEntry(getMIn(1440f) - getMIn(60f) - getMIn(45f) - getMIn(120f), ""))
        strings.add(PieEntry(getMIn(120f), ""))
        val dataSet = PieDataSet(strings, "")
        val colors = ArrayList<Int>()
        colors.add(getColor(R.color.blue_sleep_color))
        colors.add(getColor(R.color.yellow_sleep_color))
        colors.add(getColor(R.color.gray_date_text_color))
        colors.add(getColor(R.color.deepblue_sleep_color))
        dataSet.colors = colors
        val pieData = PieData(dataSet)
        pieData.setDrawValues(false)  //不显示文字
        val description = Description()
        description.setText("")
        sleepChart.setDescription(description)
        sleepChart.setHoleRadius(0f)
        sleepChart.setData(pieData)
        sleepChart.setTransparentCircleRadius(0f)
        sleepChart.setDrawCenterText(false)
        sleepChart.setDrawEntryLabels(false)
        sleepChart.setUsePercentValues(false)  //显示成百分比
        sleepChart.isRotationEnabled = false// 手动旋转
        sleepChart.animateXY(1000, 1000)  //设置动画
        val l: Legend = sleepChart.legend //获取图例对象
        l.isEnabled = false //禁用图例,直接禁用X轴会显示不全
        sleepChart.invalidate()

    }

    fun getMIn(min: Float): Float {
        return min / 1440 * 100
    }

    //绘制睡眠图表所需颜色
    private fun getColorAyyay(type: Int): List<Int> {
    var colorList= mutableListOf<Int>()
        when (type) {
            SleepDateType.light -> {
                colorList.add(getColor(R.color.blue_sleep_color))
            }
            SleepDateType.deep -> {
                colorList.add(getColor(R.color.deepblue_sleep_color))
            }
            SleepDateType.wake -> {
                colorList.add(getColor(R.color.yellow_sleep_color))
            }
            else -> {
            }
        }
        return colorList
    }

}
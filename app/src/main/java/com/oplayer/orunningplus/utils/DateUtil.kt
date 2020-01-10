package com.oplayer.orunningplus.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import com.oplayer.common.utils.Slog
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.orunningplus.utils
 * @ClassName:      DateUtil
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2020/1/10 14:56
 */

/**
 * 日期操作工具类.
 *
 * @author zhangxiong
 * @version 1.0
 * @Project ERPForAndroid
 * @Package com.ymerp.android.tools
 * @Date 2017年5月11日
 */
@SuppressLint("SimpleDateFormat")
class DateUtil {
    /**
     * 获得天数差
     *
     * @param begin
     * @param end
     * @return
     */
    fun getDayDiff(begin: Date, end: Date): Long {
        var day: Long = 1
        if (end.time < begin.time) {
            day = -1
        } else if (end.time == begin.time) {
            day = 1
        } else {
            day += (end.time - begin.time) / (24 * 60 * 60 * 1000)
        }
        return day
    }

    companion object {
        private const val FORMAT = "yyyy-MM-dd HH:mm:ss"
        private val datetimeFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        private val dateFormat =
            SimpleDateFormat("yyyy-MM-dd")
        private val timeFormat = SimpleDateFormat("HH:mm:ss")
        /**
         * 字符串转时间
         *
         * @param str
         * @param format
         * @return
         */
        @JvmOverloads
        fun str2Date(str: String?, format: String? = null): Date? {
            var format = format
            if (str == null || str.length == 0) {
                return null
            }
            if (format == null || format.length == 0) {
                format = FORMAT
            }
            var date: Date? = null
            try {
                val sdf = SimpleDateFormat(format)
                date = sdf.parse(str)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return date
        }

        @JvmOverloads
        fun str2Calendar(str: String?, format: String? = null): Calendar? {
            val date = str2Date(str, format) ?: return null
            val c = Calendar.getInstance()
            c.time = date
            return c
        }

        @JvmOverloads
        fun date2Str(c: Calendar?, format: String? = null): String? {
            return if (c == null) {
                null
            } else date2Str(c.time, format)
        }

        @JvmOverloads
        fun date2Str(
            d: Date?,
            format: String? = null
        ): String? { // yyyy-MM-dd HH:mm:ss
            var format = format
            if (d == null) {
                return null
            }
            if (format == null || format.length == 0) {
                format = FORMAT
            }
            val sdf = SimpleDateFormat(format)
            return sdf.format(d)
        }

        /**
         * 获得当前日期的字符串格式
         *
         * @param format
         * @return
         */
        fun getCurDateStr(format: String?): String? {
            val c = Calendar.getInstance()
            return date2Str(c, format)
        }

        /**
         * 格式到秒
         *
         * @param time
         * @return
         */
        fun getMillon(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time)
        }

        /**
         * 格式到天
         *
         * @param time
         * @return
         */
        fun getDay(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd").format(time)
        }

        /**
         * 格式到毫秒
         *
         * @param time
         * @return
         */
        fun getSMillon(time: Long): String {
            return SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time)
        }

        /**
         * 字符串转换到时间格式
         *
         * @param dateStr   需要转换的字符串
         * @param formatStr 需要格式的目标字符串 举例 yyyy-MM-dd
         * @return Date 返回转换后的时间
         * @throws ParseException 转换异常
         */
        fun StringToDate(dateStr: String?, formatStr: String?): Date? {
            val sdf: DateFormat = SimpleDateFormat(formatStr)
            var date: Date? = null
            try {
                date = sdf.parse(dateStr)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return date
        }


        /**
         * 把字符串转化为时间格式
         *
         * @param timestamp
         * @return
         */
        fun getStandardTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("MM月dd日 HH:mm")
            val date = Date(timestamp * 1000)
            sdf.format(date)
            return sdf.format(date)
        }

        /**
         * 获得当前日期时间 日期时间格式yyyy-MM-dd HH:mm:ss
         *
         * @return
         */
        fun currentDatetime(): String {
            return datetimeFormat.format(now())
        }

        /**
         * 获得当前日期时间 日期时间格式yyyy-MM-dd
         *
         * @return
         */
        fun currentDate(): String {
            return dateFormat.format(now())
        }

        /**
         * 格式化日期时间 日期时间格式yyyy-MM-dd HH:mm:ss
         *
         * @return
         */
        fun formatDatetime(date: Date?): String {
            return datetimeFormat.format(date)
        }

        /**
         * 获得当前时间 时间格式HH:mm:ss
         *
         * @return
         */
        fun currentTime(): String {
            return timeFormat.format(now())
        }

        /**
         * 格式化时间 时间格式HH:mm:ss
         *
         * @return
         */
        fun formatTime(date: Date?): String {
            return timeFormat.format(date)
        }

        /**
         * 获得当前时间的`java.util.Date`对象
         *
         * @return
         */
        fun now(): Date {
            return Date()
        }

        fun calendar(): Calendar {
            val cal =
                GregorianCalendar.getInstance(Locale.CHINESE)
            cal.firstDayOfWeek = Calendar.SUNDAY
            return cal
        }

        /**
         * 获得当前时间的毫秒数
         *
         *
         * 详见[System.currentTimeMillis]
         *
         * @return
         */
        fun millis(): Long {
            return System.currentTimeMillis()
        }

        /**
         * 获得当前Chinese月份
         *
         * @return
         */
        fun month(): Int {
            return calendar()[Calendar.MONTH] + 1
        }

        /**
         * 获得月份中的第几天
         *
         * @return
         */
        fun dayOfMonth(): Int {
            return calendar()[Calendar.DAY_OF_MONTH]
        }

        /**
         * 今天是星期的第几天
         *
         * @return
         */
        fun dayOfWeek(): Int {
            return calendar()[Calendar.DAY_OF_WEEK]
        }

        /**
         * 今天是年中的第几天
         *
         * @return
         */
        fun dayOfYear(): Int {
            return calendar()[Calendar.DAY_OF_YEAR]
        }

        /**
         * 判断原日期是否在目标日期之前
         *
         * @param src
         * @param dst
         * @return
         */
        fun isBefore(src: Date, dst: Date?): Boolean {
            return src.before(dst)
        }

        /**
         * 判断原日期是否在目标日期之后
         *
         * @param src
         * @param dst
         * @return
         */
        fun isAfter(src: Date, dst: Date?): Boolean {
            return src.after(dst)
        }

        /**
         * 判断两日期是否相同
         *
         * @param date1
         * @param date2
         * @return
         */
        fun isEqual(date1: Date, date2: Date?): Boolean {
            return date1.compareTo(date2) == 0
        }

        /**
         * 判断某个日期是否在某个日期范围
         *
         * @param beginDate 日期范围开始
         * @param endDate   日期范围结束
         * @param src       需要判断的日期
         * @return
         */
        fun between(
            beginDate: Date,
            endDate: Date,
            src: Date?
        ): Boolean {
            return beginDate.before(src) && endDate.after(src)
        }

        /**
         * 获得当前月的最后一天
         *
         *
         * HH:mm:ss为0，毫秒为999
         *
         * @return
         */
        fun lastDayOfMonth(): Date {
            val cal = calendar()
            cal[Calendar.DAY_OF_MONTH] = 0 // M月置零
            cal[Calendar.HOUR_OF_DAY] = 0 // H置零
            cal[Calendar.MINUTE] = 0 // m置零
            cal[Calendar.SECOND] = 0 // s置零
            cal[Calendar.MILLISECOND] = 0 // S置零
            cal[Calendar.MONTH] = cal[Calendar.MONTH] + 1 // 月份+1
            cal[Calendar.MILLISECOND] = -1 // 毫秒-1
            return cal.time
        }

        /**
         * 获得当前月的第一天
         *
         *
         * HH:mm:ss SS为零
         *
         * @return
         */
        fun firstDayOfMonth(): Date {
            val cal = calendar()
            cal[Calendar.DAY_OF_MONTH] = 1 // M月置1
            cal[Calendar.HOUR_OF_DAY] = 0 // H置零
            cal[Calendar.MINUTE] = 0 // m置零
            cal[Calendar.SECOND] = 0 // s置零
            cal[Calendar.MILLISECOND] = 0 // S置零
            return cal.time
        }

        /**
         * 将字符串日期时间转换成java.util.Date类型 日期时间格式yyyy-MM-dd HH:mm:ss
         *
         * @param datetime
         * @return
         */
        @Throws(ParseException::class)
        fun parseDatetime(datetime: String?): Date {
            return datetimeFormat.parse(datetime)
        }

        /**
         * 将字符串日期转换成java.util.Date类型 日期时间格式yyyy-MM-dd
         *
         * @param date
         * @return
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun parseDate(date: String?): Date {
            return dateFormat.parse(date)
        }

        /**
         * 将字符串日期转换成java.util.Date类型 时间格式 HH:mm:ss
         *
         * @param time
         * @return
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun parseTime(time: String?): Date {
            return timeFormat.parse(time)
        }

        /**
         * 根据自定义pattern将字符串日期转换成java.util.Date类型
         *
         * @param datetime
         * @param pattern
         * @return
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun parseDatetime(datetime: String?, pattern: String?): Date {
            val format =
                datetimeFormat.clone() as SimpleDateFormat
            format.applyPattern(pattern)
            return format.parse(datetime)
        }

        /**
         * 将时间戳转换为时间
         *
         * @param s
         * @return
         */
        fun stampToDate(s: String?): String {
            val res: String
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm")
            val lt = java.lang.Long.valueOf(s!!)
            val date = Date(lt)
            res = simpleDateFormat.format(date)
            return res
        }

        fun stampToDate(s: String?, format: String?): String {
            val res: String
            val simpleDateFormat = SimpleDateFormat(format)
            val lt = java.lang.Long.valueOf(s!!)
            val date = Date(lt)
            res = simpleDateFormat.format(date)
            return res
        }

        /**
         * 将时间戳转换为时间
         *
         * @param s
         * @return
         */
        fun conventDate(s: String?): String {
            val res: String
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val lt = java.lang.Long.valueOf(s!!)
            val date = Date(lt)
            res = simpleDateFormat.format(date)
            return res
        }

        fun conventDate2503(s: String?): String {
            val res: String
            val simpleDateFormat =
                SimpleDateFormat("yyyy|MM|dd|HH|mm|ss")
            var date: Date? = Date()
            try {
                date = simpleDateFormat.parse(s)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val simpleDateFormat1 =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            res = simpleDateFormat1.format(date)
            return res
        }

        /**
         * 将时间转换为时间戳
         *
         * @param s
         * @return
         */
        @Throws(ParseException::class)
        fun dateToStamp(s: String?): String {
            val res: String
            val simpleDateFormat =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = simpleDateFormat.parse(s)
            val ts = date.time
            res = ts.toString()
            return res
        }

        /**
         * 时间戳间隔
         *
         * @param begin
         * @param end
         * @return 分钟
         */
        fun getTwoTimeStampSpace(begin: String?, end: String?): Long {
            val b = java.lang.Long.valueOf(begin!!)
            val e = java.lang.Long.valueOf(end!!)
            return (e - b) / (1000 * 60)
        }

        /**
         * 时间戳间隔
         *
         * @param begin
         * @param end
         * @return 秒
         */
        fun getTwoTimeStampSpaceS(begin: String?, end: String?): Long {
            val b = java.lang.Long.valueOf(begin!!)
            val e = java.lang.Long.valueOf(end!!)
            return (e - b) / 1000
        }


        /**
         * 比较时间大小
         *
         * @param begin
         * @param end
         * @return
         */
        fun compareDate(begin: String?, end: String?): Int {
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
            try {
                val beginDate = df.parse(begin)
                val endDate = df.parse(end)
                return if (beginDate.time < endDate.time) {
                    1
                } else if (beginDate.time > endDate.time) {
                    -1
                } else {
                    0
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return 0
        }

        /**
         * 获得年份
         *
         * @param date
         * @return
         */
        fun getYear(date: Date?): Int {
            val c = Calendar.getInstance()
            c.time = date
            return c[Calendar.YEAR]
        }

        /**
         * 获得月份
         *
         * @param date
         * @return
         */
        fun getMonth(date: Date?): Int {
            val c = Calendar.getInstance()
            c.time = date
            return c[Calendar.MONTH] + 1
        }

        /**
         * 获得星期几
         *
         * @param date
         * @return
         */
        fun getWeek(date: Date?): Int {
            val c = Calendar.getInstance()
            c.time = date
            return c[Calendar.DAY_OF_WEEK]
        }

        /**
         * 获得星期几的前一天
         *
         * @param date
         * @return
         */
        fun getPreWeek(date: Date): Int {
            val preDate =
                getPreDay(date.year, date.month, date.day)
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val c = Calendar.getInstance()
            try {
                c.time = formatter.parse(preDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                c.time = Date()
            }
            return c[Calendar.DAY_OF_WEEK]
        }

        /**
         * 获得日期
         *
         * @param date
         * @return
         */
        fun getDay(date: Date): Int {
            val c = Calendar.getInstance()
            c.time = date
            return c[Calendar.DATE]
        }

        /**
         * 获得小时
         *
         * @param date
         * @return
         */
        fun getHour(date: Date?): Int {
            val c = Calendar.getInstance()
            c.time = date
            return c[Calendar.HOUR_OF_DAY]
        }

        /**
         * 获得当前分钟
         *
         * @param date
         * @return
         */
        fun getMinute(date: Date?): Int {
            val c = Calendar.getInstance()
            c.time = date
            return c[Calendar.MINUTE]
        }

        /**
         * 获得当前秒
         *
         * @param date
         * @return
         */
        fun getSecond(date: Date?): Int {
            val c = Calendar.getInstance()
            c.time = date
            return c[Calendar.SECOND]
        }

        /**
         * 得到指定月的天数
         */
        fun getMonthLastDay(year: Int, month: Int): Int {
            val a = Calendar.getInstance()
            a[Calendar.YEAR] = year
            a[Calendar.MONTH] = month - 1
            a[Calendar.DATE] = 1 //把日期设置为当月第一天
            //roll()函数处理，只会比相应的字段进行处理，不会智能的对其它字段也进行逻辑上的改变。但是add()函数会在逻辑上改变其它字段，使结果正确。
            a.roll(Calendar.DATE, -1) //日期回滚一天，也就是最后一天
            return a[Calendar.DATE]
        }

        /**
         * 获得指定月的第一天
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getMonthStartDay(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                cal[Calendar.DATE] = 1 //把日期设置为当月第一天
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 获得某年某月某日的前一天
         *
         * @param year
         * @param month
         * @return yyyy-MM-dd
         */
        fun getPreDay(year: Int, month: Int, day: Int): String {
            var year = year
            var month = month
            var day = day
            if (day != 1) { // 判断是否为某月月初
                day--
            } else {
                if (month != 1) { // 如果不是1月的话，那么就是上月月末
                    month--
                    day = getMonthLastDay(year, month)
                } else { // 如果是1月的话，那么就是上年的12月31日
                    year--
                    month = 12
                    day = 31
                }
            }
            return "$year-$month-$day"
        }

        /**
         * 获得某年某月某日的后一天
         *
         * @param year
         * @param month
         * @param day
         * @return yyyy-MM-dd
         */
        fun getNextDay(year: Int, month: Int, day: Int): String {
            var year = year
            var month = month
            var day = day
            if (day != getMonthLastDay(year, month)) { // 判断是否为某月月末
                day++
            } else {
                if (month != 12) { // 如果不是12月的话，那么就是次月月初
                    month++
                    day = 1
                } else { // 如果是12月的话，那么就是次年的1月1日
                    year++
                    day = 1
                    month = day
                }
            }
            return "$year-$month-$day"
        }

        /**
         * 获得指定日期的周开始日期
         * 周日为每周第一天
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getWeekStartStr(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
                //            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
                //            if(1 == dayWeek) {
                //                cal.add(Calendar.DAY_OF_MONTH, -1);
                //            }
                //            cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天
                cal.firstDayOfWeek = Calendar.SUNDAY //设置一个星期的第一天
                val day = cal[Calendar.DAY_OF_WEEK] //获得当前日期是一个星期的第几天
                cal.add(
                    Calendar.DATE,
                    cal.firstDayOfWeek - day
                ) //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 获得指定日期的周结束日期
         * 周日为每周第一天（周一为每周第一天时需要处理）
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getWeekEndStr(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
                //            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
                //            if(1 == dayWeek) {
                //                cal.add(Calendar.DAY_OF_MONTH, -1);
                //            }
                //            cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天
                cal.firstDayOfWeek = Calendar.SUNDAY //设置一个星期的第一天
                val day = cal[Calendar.DAY_OF_WEEK] //获得当前日期是一个星期的第几天
                cal.add(
                    Calendar.DATE,
                    cal.firstDayOfWeek - day
                ) //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
                cal.add(Calendar.DATE, 6)
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 获得指定日期的上一周第一天日期
         * 周日为每周第一天（周一为每周第一天时需要处理）
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getPreviousWeekStr(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                //            //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
                //            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
                //            if(1 == dayWeek) {
                //                cal.add(Calendar.DAY_OF_MONTH, -1);
                //            }
                //            cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天
                cal.firstDayOfWeek = Calendar.SUNDAY //设置一个星期的第一天
                val day = cal[Calendar.DAY_OF_WEEK] //获得当前日期是一个星期的第几天
                cal.add(
                    Calendar.DATE,
                    cal.firstDayOfWeek - day
                ) //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
                cal.add(Calendar.DATE, -7)
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 获得指定日期的下一周第一天日期
         * 周日为每周第一天
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getNextWeekStr(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                //            //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
                //            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
                //            if(1 == dayWeek) {
                //                cal.add(Calendar.DAY_OF_MONTH, -1);
                //            }
                cal.firstDayOfWeek = Calendar.SUNDAY //设置一个星期的第一天
                val day = cal[Calendar.DAY_OF_WEEK] //获得当前日期是一个星期的第几天
                cal.add(
                    Calendar.DATE,
                    cal.firstDayOfWeek - day
                ) //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
                cal.add(Calendar.DATE, 7)
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 获得指定月的上一个月
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getPreviousMonthStr(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                cal.add(Calendar.MONTH, -1)
                cal[Calendar.DATE] = 1 //把日期设置为当月第一天
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 获得指定月的下一个月
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getNextMonthStr(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                cal.add(Calendar.MONTH, 1)
                cal[Calendar.DATE] = 1 //把日期设置为当月第一天
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 获得指定年的下一个年
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getNextYearStr(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                cal.add(Calendar.YEAR, 1)
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 获得指定年的下一个年
         *
         * @param dateStr yyyy-MM-dd
         * @return yyyy-MM-dd
         */
        fun getPreviousYearStr(dateStr: String?): String {
            return try {
                val sdf =
                    SimpleDateFormat("yyyy-MM-dd") //设置时间格式
                val cal = Calendar.getInstance()
                val time = sdf.parse(dateStr)
                cal.time = time
                cal.add(Calendar.YEAR, -1)
                sdf.format(cal.time)
            } catch (e: ParseException) {
                ""
            }
        }

        /**
         * 判断两个时间是否相等
         *
         * @param d1
         * @param d2
         * @return
         */
        @SuppressLint("WrongConstant")
        fun isSameDate(d1: Date?, d2: Date?): Boolean {
            if (null == d1 || null == d2) return false
            //return getOnlyDate(d1).equals(getOnlyDate(d2));
            val cal1 = Calendar.getInstance()
            cal1.time = d1
            val cal2 = Calendar.getInstance()
            cal2.time = d2
            return cal1[0] == cal2[0] && cal1[1] == cal2[1] && cal1[6] == cal2[6]
        }

        /**
         * 将短时间格式字符串转换为时间 yyyy-MM-dd
         *
         * @param strDate
         * @return
         */
        fun strToDate(strDate: String): Date? {
            val formatter =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var strtodate: Date? = null
            try {
                strtodate = formatter.parse(strDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                Slog.d("strToDate 转换失败  $e  strDate $strDate")
            }
            return strtodate
        }

        fun strToDate(strDate: String, format: String?): Date? {
            val formatter = SimpleDateFormat(format)
            var strtodate: Date? = null
            try {
                strtodate = formatter.parse(strDate)
            } catch (e: ParseException) {
                e.printStackTrace()
                Slog.d("strToDate 转换失败  $e  strDate $strDate")
            }
            return strtodate
        }

        // 格式化日期数据
        fun arrangeDate(dateStr: String): String {
            val dates = dateStr.split("-").toTypedArray()
            val year = dates[0]
            var month = dates[1]
            var day = dates[2]
            if (month.length == 1) {
                month = "0$month"
            }
            if (day.length == 1) {
                day = "0$day"
            }
            return "$year-$month-$day"
        }

        /**
         * 计算两个时间的差值  单位分钟
         *
         * @param startTime
         * @param endTime
         * @return
         */
        fun timeDifference(startTime: String?, endTime: String?): Int {
            val simpleFormat =
                SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            var from: Long = 0
            var to: Long = 0
            try {
                from = simpleFormat.parse(startTime).time
                to = simpleFormat.parse(endTime).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ((to - from) / (1000 * 60)).toInt()
        }

        /**
         * //时间戳转化为Sting或Date
         *
         * @param date
         * @return
         * @throws ParseException
         */
        @Throws(ParseException::class)
        fun getDate(date: String?): Date {
            val format =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val time = java.lang.Long.valueOf(date!!)
            val d = format.format(time)
            return format.parse(d)
        }

        /**
         * 获取精确到秒的时间戳
         */
        fun getSecondTimestampTwo(date: Date?): Int {
            if (null == date) {
                return 0
            }
            val timestamp = (date.time / 1000).toString()
            return Integer.valueOf(timestamp)
        }

        fun Local2UTC(currTime: String?): String {
            val utcFormater =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //UTC时间格式
            var CurrDate: Date? = null
            try {
                CurrDate = utcFormater.parse(currTime)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            sdf.timeZone = TimeZone.getTimeZone("gmt")
            return sdf.format(CurrDate)
        }

        /**
         * UTC时间 ---> 当地时间     * @param utcTime   UTC时间     * @return
         */
        fun utc2Local(utcTime: String?): String {
            val utcFormater =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //UTC时间格式
            utcFormater.timeZone = TimeZone.getTimeZone("UTC")
            var gpsUTCDate: Date? = null
            try {
                gpsUTCDate = utcFormater.parse(utcTime)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val localFormater =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss") //当地时间格式
            localFormater.timeZone = TimeZone.getDefault()
            return localFormater.format(gpsUTCDate!!.time)
        }

        /**
         * 字符串时间 ---> 毫秒数    * @param utcTime   UTC时间     * @return
         */
        fun str2Mills(strTime: String?): Long {
            val c = Calendar.getInstance()
            try {
                c.time = datetimeFormat.parse(strTime)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return c.timeInMillis
        }

        /**
         * 秒转时分
         * 28800 ->  08:00
         *
         * @param time
         * @return
         */
        fun secToTime(time: Int): String {
            val stringBuilder = StringBuilder()
            val hour = time / 3600
            val minute = time / 60 % 60
            //        Integer second = time % 60;
            if (hour < 10) {
                stringBuilder.append("0")
            }
            stringBuilder.append(hour)
            stringBuilder.append(":")
            if (minute < 10) {
                stringBuilder.append("0")
            }
            stringBuilder.append(minute)
            return stringBuilder.toString()
        }

        /**
         * 时分转秒
         * 08:00 -> 28800
         *
         * @param time
         * @return
         */
        fun secToTime(time: String): Int {
            val str = time.split(":").toTypedArray()
            val hour = Integer.valueOf(str[0])
            val minute = Integer.valueOf(str[1])
            var second = 0
            second = second + hour * 3600
            second = second + minute * 60
            return second
        }

        /**
         * 将时间转换为时间戳
         *
         * @param
         * @return
         */
        fun dateToStr(date: Date?, formate: String?): String {
            val simpleDateFormat = SimpleDateFormat(formate)
            return simpleDateFormat.format(date)
        }

        fun is24Hour(mContext: Context): Boolean {
            var is24 = false
            is24 = try {
                val cv: ContentResolver =mContext.getContentResolver()
                val strTimeFormat = Settings.System.getString(
                    cv,
                    Settings.System.TIME_12_24
                )
                strTimeFormat == "24"
            } catch (e: Exception) {
                Slog.d("获取小时制失败  e  $e")
                false
            }
            return is24
        }
    }
}
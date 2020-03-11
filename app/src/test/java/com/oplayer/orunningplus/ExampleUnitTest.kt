package com.oplayer.orunningplus

import com.oplayer.orunningplus.bean.DeviceInfo
import com.oplayer.orunningplus.utils.DateUtil
import com.vicpin.krealmextensions.queryFirst
import org.junit.Test

import org.junit.Assert.*
import java.util.*
import java.util.logging.Handler

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        print("当前是星期几   ${ DateUtil.getWeek(Date())}")
    }


}

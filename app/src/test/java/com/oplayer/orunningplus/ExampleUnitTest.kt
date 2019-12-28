package com.oplayer.orunningplus

import com.oplayer.orunningplus.bean.DeviceInfo
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

    private var queueFun = LinkedList<Function>()

    private var currentFun: (() -> Unit?)? = null // 当前任务

    fun addFunc(function: () -> Unit?) {
        var func = Function(function)
        doFunc(func)
    }

    fun finishFunc() {
        doFunc(null)
    }

    private fun doFunc(func: Function?) {
        if (func != null) {
            queueFun.offer(func)
        } else {
            currentFun = null
        }
        if (currentFun == null) {
            if (queueFun.size != 0) {
                var funnow = queueFun.poll()
                currentFun = funnow.function
                currentFun?.invoke()
            }
        }
    }

    data class Function(var function: () -> Unit? = {})


    @Test
    fun addition_isCorrect() {

        print("test function")
        addFunc { chage() }
        addFunc { add() }
        addFunc { del() }
        addFunc { edit() }

//        Thread.sleep(500)
//        finishFunc()
//        Thread.sleep(4000)
//        finishFunc()
//        Thread.sleep(1000)
//        finishFunc()
//        Thread.sleep(3000)
//        finishFunc()
    }


    fun add() {
        print("this fun is add")

    }

    fun del() {

        print("this fun is del")

    }

    fun edit() {
        print("this fun is edit")

    }

    fun chage() {
        print("this fun is chage")



    }

    fun print(str: String) {
        System.out.println(str)
    }
}

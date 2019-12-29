package com.oplayer.common.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.common
 * @ClassName:      AppManger
 * @Description:     App管理方法
 * @Author:         Ben
 * @CreateDate:     2019/7/23 10:50
 */

class AppManager private constructor() {

    private val activityStack: Stack<Activity> = Stack()

    companion object {
        val instance: AppManager by lazy { AppManager() }
    }

    /*
          Activity入栈
       */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /*
        Activity出栈
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    /*
        获取当前栈顶
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /*
        清理栈
     */
    fun clearStack() {
      activityStack.forEach { it.finish() }
        activityStack.clear()
    }

    /*
        退出应用程序
     */
    fun exitApp(context: Context) {
        clearStack()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }



    fun isServiceRunning(context: Context, className: String): Boolean{
        if(className.isNullOrEmpty()){
            return false
        }
        var isRunning: Boolean = false
        var activityManager: ActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var serviceList: List<ActivityManager.RunningServiceInfo> = activityManager.getRunningServices(Int.MAX_VALUE)
        if(serviceList.isEmpty()){
            return false
        }
        serviceList.forEach { item ->
            if(item.service.className == className){
                isRunning = true
                return@forEach
            }
        }
        return isRunning
    }



}



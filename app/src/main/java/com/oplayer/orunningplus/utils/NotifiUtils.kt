package com.oplayer.common.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.oplayer.common.common.CustomizedPackName
import com.oplayer.orunningplus.R
import java.util.*

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.utils
 * @ClassName:      NotifiUtils
 * @Description:     java类作用描述
 * @Author:         Ben
 * @CreateDate:     2019/9/21 11:09
 */

class NotifiUtils {

    companion object {


        /**
         * 获取Notification
         *
         * @param contentTitle 标题
         * @param contextText  内容
         */
        @RequiresApi(Build.VERSION_CODES.O)
        fun getNotification(
            mContext: Context,
            contextText: String,
            sourcesId: Int
        ): Notification {
            val notification: Notification
//            if (Build.VERSION.SDK_INT >= 26) {
            Slog.d("构建通知 Build.VERSION ${Build.VERSION.SDK_INT} ")
            val mNotificationManager =
                mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            /**
             * IMPORTANCE_NONE 关闭通知
             * IMPORTANCE_MIN 开启通知，不会弹出，但没有提示音，状态栏中无显示
             * IMPORTANCE_LOW 开启通知，不会弹出，不发出提示音，状态栏中显示
             * IMPORTANCE_DEFAULT 开启通知，不会弹出，发出提示音，状态栏中显示
             * IMPORTANCE_HIGH 开启通知，会弹出，发出提示音，状态栏中显示
             */
            val channelName = UIUtils.getString(R.string.app_name)
            val channelID = CustomizedPackName.ORunningPlus
            val channel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW)
            mNotificationManager.createNotificationChannel(channel)
            notification = Notification.Builder(mContext, channelID)
                .setContentTitle(channelName)// 设置下拉列表里的标题
                .setSmallIcon(sourcesId) // 设置状态栏内的小图标
                .setContentText(contextText) // 设置上下文内容
                .setWhen(System.currentTimeMillis()).build()// 设置该通知发生的时间
            //显示通知
//                mNotificationManager.notify(0, notification)
//            }

//            else {
//                Slog.d("构建通知 Build.VERSION ${Build.VERSION.SDK_INT} ")
//                notification = NotificationCompat.Builder(mContext)
//                    .setContentTitle(UIUtils.getString(R.string.app_name)) // 设置下拉列表里的标题
//                    .setSmallIcon(sourcesId) // 设置状态栏内的小图标
//                    .setContentText(contextText) // 设置上下文内容
//                    .setWhen(System.currentTimeMillis()).build() // 设置该通知发生的时间
//            }
            return notification
        }


    }
}
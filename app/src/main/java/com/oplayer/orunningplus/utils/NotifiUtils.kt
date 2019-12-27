package com.oplayer.common.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.oplayer.orunningplus.R

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

    companion object{


        /**
         * 获取Notification
         *
         * @param contentTitle 标题
         * @param contextText  内容
         */
        fun getNotification(
            mContext:Context,
            contextText: String,
            sourcesId: Int,
            cls: Class<*>
        ): Notification {
            var mNotificationManager = UIUtils.getContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification: Notification
            if (Build.VERSION.SDK_INT >= 26) {
                val channelName = UIUtils.getString(R.string.app_name)
                val channelID = mContext.packageName
                val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW)
                /**
                 * IMPORTANCE_NONE 关闭通知
                 * IMPORTANCE_MIN 开启通知，不会弹出，但没有提示音，状态栏中无显示
                 * IMPORTANCE_LOW 开启通知，不会弹出，不发出提示音，状态栏中显示
                 * IMPORTANCE_DEFAULT 开启通知，不会弹出，发出提示音，状态栏中显示
                 * IMPORTANCE_HIGH 开启通知，会弹出，发出提示音，状态栏中显示
                 */
                channel.setShowBadge(false)//设置是否显示角标
                mNotificationManager.createNotificationChannel(channel)
                val builder = Notification.Builder(mContext, channelID) //获取一个Notification构造器
                val nfIntent = Intent(mContext, cls)
                builder.setContentIntent(
                    PendingIntent.getActivity(
                        mContext,
                        0,
                        nfIntent,
                        0
                    )
                )// 设置PendingIntent
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            mContext.resources,
                            sourcesId
                        )
                    )// 设置下拉列表中的图标(大图标)
                    .setContentTitle(channelName)// 设置下拉列表里的标题
                    .setSmallIcon(sourcesId) // 设置状态栏内的小图标
                    .setContentText(contextText) // 设置上下文内容
                    .setWhen(System.currentTimeMillis()) // 设置该通知发生的时间

                notification = builder.build() // 获取构建好的Notification
            } else {
                val builder = NotificationCompat.Builder(mContext)
                val nfIntent = Intent(mContext, cls)
                builder.setContentIntent(
                    PendingIntent.getActivity(
                        UIUtils.getContext(),
                        0,
                        nfIntent,
                        0
                    )
                )// 设置PendingIntent
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            mContext.resources,
                            sourcesId
                        )
                    )// 设置下拉列表中的图标(大图标)
                    .setContentTitle(UIUtils.getString(R.string.app_name)) // 设置下拉列表里的标题
                    .setSmallIcon(sourcesId) // 设置状态栏内的小图标
                    .setContentText(contextText) // 设置上下文内容
                    .setWhen(System.currentTimeMillis()) // 设置该通知发生的时间
                notification = builder.build()
            }
            return notification
        }



    }




}
package com.oplayer.orunningplus.service

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.oplayer.common.common.NotifiDate
import com.oplayer.common.utils.Slog
import com.oplayer.orunningplus.bean.NotificationDate
import com.oplayer.orunningplus.event.MessageEvent
import com.vicpin.krealmextensions.query
import org.greenrobot.eventbus.EventBus


class NotificationReceiverService : NotificationListenerService() {


    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn != null) {
            var bundle = sbn.notification.extras
            val contentTitle = bundle.getString(Notification.EXTRA_TITLE)
            var contentText: String? = bundle.getString(Notification.EXTRA_TEXT)
            var contentSubtext: String? = bundle.getString(Notification.EXTRA_SUB_TEXT)
       var notifiList=     NotificationDate().query { equalTo( "pkg",sbn.packageName) }

       Slog.d("notifiList 是否包含某app  ${sbn.packageName}")
            //阻止未经允许的通知
            EventBus.getDefault().post(
                MessageEvent(
                    NotifiDate,
                    NotificationDate(sbn.tag, sbn.packageName, contentTitle, contentText)
                )
            )


        }


    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        Slog.d("onNotificationPosted")

        if (sbn != null) {
            Slog.d("onNotificationRemoved \n  packageName : ${sbn.packageName}   getTag  ${sbn.tag}  \n notification  ${sbn.notification} ")

        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Slog.d("onStartCommand  intent ${intent}   flags  ${flags}  startId ${startId}")

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Slog.d("onUnbind   intent  ${intent}")
        return super.onUnbind(intent)
    }
}

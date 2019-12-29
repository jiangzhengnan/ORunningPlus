package com.oplayer.orunningplus.service

import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresApi
import com.oplayer.common.utils.Slog

class NotificationReceiverService : NotificationListenerService() {



    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn != null) {
            Slog.d("onNotificationRemoved \n  packageName : ${sbn.packageName  }   getTag  ${sbn.tag}  \n notification  ${sbn.notification} ")
        }


    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        if (sbn != null) {
            Slog.d("onNotificationRemoved \n  packageName : ${sbn.packageName  }   getTag  ${sbn.tag}  \n notification  ${sbn.notification} ")

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

package com.oplayer.common.base

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.oplayer.common.utils.NotifiUtils
import com.oplayer.orunningplus.OSportApplciation
import com.oplayer.common.utils.NotifiUtils.Companion.getNotification
import com.oplayer.common.utils.Slog
import com.oplayer.common.utils.UIUtils
import com.oplayer.orunningplus.R
import com.oplayer.orunningplus.event.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 *
 * @ProjectName:    ORunningPlus
 * @Package:        com.oplayer.common.presenter
 * @ClassName:      BaseService
 * @Description:    服务父类
 * @Author:         Ben
 * @CreateDate:     2019/7/26 14:30
 */
abstract class BaseService : Service() {


    val NOTIFICATION_ID: Int = R.string.app_name
    lateinit var mContext: Context
    lateinit var mNotificationManager: NotificationManager



    override fun onCreate() {
        super.onCreate()
        registerEventBus(this)
        mContext = UIUtils.getContext()
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        unregisterEventBus(this)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    fun isEventBusRegisted(subscribe: Any): Boolean {
        return EventBus.getDefault().isRegistered(subscribe)
    }

    fun registerEventBus(subscribe: Any) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe)
        }
    }

    fun unregisterEventBus(subscribe: Any) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe)
        }
    }

    @Subscribe
    fun onMessageEvent(event: MessageEvent) {
        onGetEvent(event)
    }

    protected abstract fun onGetEvent(event: MessageEvent)

}
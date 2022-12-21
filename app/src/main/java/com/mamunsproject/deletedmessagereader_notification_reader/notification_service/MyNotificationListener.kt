package com.mamunsproject.deletedmessagereader_notification_reader.notification_service

import android.app.Notification
import android.content.Intent
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.util.*

class MyNotificationListener : NotificationListenerService() {


    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val notificationPostTime = sbn!!.postTime
        if (notificationPostTime == 0L) {
            Log.d("MyNotificationListener", "Notification post time: $notificationPostTime")
            Log.d("MyNotificationListener", "StatusBarNotification: $sbn")
        }

        val packageName = sbn!!.packageName
        val text = sbn.notification.extras.getString(Notification.EXTRA_TEXT)
        val appName = sbn.notification.extras.getString(Notification.EXTRA_BIG_TEXT)
        val title = sbn.notification.extras.getString(Notification.EXTRA_TITLE)
        var postTime = sbn.postTime

        val currentDateTimeString: String = DateFormat.getDateInstance().format(Date())


        Log.d(
            "NOtificatinxxx",
            "onNotificationPosted: PackageName $packageName  Text $text  Title $title   "
        )

        var intent = Intent("com.example.notification_text")
        intent.putExtra("notificationText", text)
        intent.putExtra("notificationAppName", appName)
        intent.putExtra("notificationPackageName", packageName)
        intent.putExtra("notificationTitle", title)
        intent.putExtra("notificationTime", postTime)
        intent.putExtra("notificationDate", currentDateTimeString)


        Log.d(
            "NOtificatinxxx",
            "onNotificationPosted: PackageName $packageName  Text $text  Title $title   "
        )

        sendBroadcast(intent)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        val title = sbn.notification.extras.getString(Notification.EXTRA_TITLE)
        val text = sbn.notification.extras.getString(Notification.EXTRA_TEXT)
        val icon = sbn.notification.smallIcon
        // Perform cleanup tasks with the notification data
    }

}
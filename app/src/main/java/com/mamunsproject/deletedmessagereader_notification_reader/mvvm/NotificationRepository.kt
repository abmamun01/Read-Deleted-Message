package com.mamunsproject.deletedmessagereader_notification_reader.mvvm

import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationEntity

class NotificationRepository(var db: NotificationDatabase) {
    suspend fun insert(apps: NotificationEntity) = db.getNotificationDao().insertApp(apps)
    suspend fun delete(apps: NotificationEntity) = db.getNotificationDao().deleteApp(apps)

    fun getAllNotification() = db.getNotificationDao().getAllNotification()
}
package com.mamunsproject.deletedmessagereader_notification_reader.mvvm

import androidx.lifecycle.ViewModel
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NotificationVIewModel(var repository: NotificationRepository) : ViewModel() {

    fun insert(apps: NotificationEntity) = GlobalScope.launch {
        repository.insert(apps)
    }


    fun delete(apps: NotificationEntity) = GlobalScope.launch {
        repository.delete(apps)
    }




    fun getAllNotification() =
        repository.getAllNotification()


}
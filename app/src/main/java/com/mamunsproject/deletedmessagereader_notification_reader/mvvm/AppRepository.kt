package com.mamunsproject.deletedmessagereader_notification_reader.mvvm

import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel

class AppRepository(var db: AppDatabase) {

    suspend fun insert(apps: AppModel) = db.getAppDao().insertApp(apps)
    suspend fun delete(apps: AppModel) = db.getAppDao().deleteApp(apps)
    suspend fun update(isChecked: Boolean, id: Int) = db.getAppDao().updateApp(isChecked, id)
     fun getAllApp() = db.getAppDao().getAllApp()
}
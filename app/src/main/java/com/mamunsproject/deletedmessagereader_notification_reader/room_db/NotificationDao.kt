package com.mamunsproject.deletedmessagereader_notification_reader.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface NotificationDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApp(apps: NotificationEntity)


    @Delete
    fun deleteApp(apps: NotificationEntity)


    @Query("SELECT * FROM notification_table")
    fun getAllNotification(): List<NotificationEntity>



}
package com.mamunsproject.deletedmessagereader_notification_reader.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotificationEntity::class], version = 6)
abstract class NotificationDatabase : RoomDatabase() {

    abstract fun getNotificationDao(): NotificationDao


    companion object {
        @Volatile
        private var instanceOfApp: NotificationDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instanceOfApp ?: synchronized(LOCK) {
            instanceOfApp ?: createDatabase(context).also {
                instanceOfApp = it
            }


        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NotificationDatabase::class.java,
                "Notification6.db"
            ).build()
    }
}
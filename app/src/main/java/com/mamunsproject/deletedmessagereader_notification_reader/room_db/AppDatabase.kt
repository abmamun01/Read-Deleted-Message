package com.mamunsproject.deletedmessagereader_notification_reader.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AppModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao


    companion object {
        @Volatile
        private var instanceOfApp: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instanceOfApp ?: synchronized(LOCK) {
            instanceOfApp ?: createDatabase(context).also {
                instanceOfApp = it
            }


        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDb1.db"
            ).build()
    }
}
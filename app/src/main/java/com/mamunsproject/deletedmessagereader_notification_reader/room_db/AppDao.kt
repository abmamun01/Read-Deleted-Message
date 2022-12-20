package com.mamunsproject.deletedmessagereader_notification_reader.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface AppDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertApp(apps: AppModel)


    @Delete
    fun deleteApp(apps: AppModel)


    @Query("SELECT * FROM app_table")
    fun getAllApp(): List<AppModel>


    @Query("UPDATE app_table SET isChecked=:isChecked WHERE id==:id")
    fun updateApp(isChecked: Boolean, id: Int)


}
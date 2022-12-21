package com.mamunsproject.deletedmessagereader_notification_reader.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notification_table")
data class NotificationEntity(
    val text: String,
    val package_name: String,
    var app_name: String,
    var timeWhenPosted: Int,
    var date: Int
) {


    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    constructor(text: String, timeWhenPosted: Int, date: Int) : this(text, "",  "app_name", 3, 3)


}
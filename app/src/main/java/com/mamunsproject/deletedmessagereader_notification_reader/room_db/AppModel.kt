package com.mamunsproject.deletedmessagereader_notification_reader.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "app_table")
data class AppModel(
    val package_name: String,
    val app_name: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val appImage: ByteArray,
    var isChecked: Boolean,
    var timeWhenPosted: Int,
    var date: Int
) {


    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    constructor(app_name: String, package_name: String, appImage: ByteArray) : this(
        app_name,
        package_name,
        package_name.toByteArray(),
        false,
        3,
        3
    )

    constructor(
        app_name: String,
        package_name: String,
        appImage: ByteArray,
        isChecked: Boolean
    ) : this(
        app_name,
        package_name,
        appImage,
        false,
        3,
        3
    )



}
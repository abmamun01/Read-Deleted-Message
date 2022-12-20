package com.mamunsproject.deletedmessagereader_notification_reader.mvvm

import androidx.lifecycle.ViewModel
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppViewModel(var repository: AppRepository) : ViewModel() {

    fun insert(apps: AppModel) = GlobalScope.launch {
        repository.insert(apps)
    }


    fun delete(apps: AppModel) = GlobalScope.launch {
        repository.delete(apps)
    }

    fun update(isChecked: Boolean, id: Int) = GlobalScope.launch {
        repository.update(isChecked, id)
    }


    fun getAllAppss() =
        repository.getAllApp()





}
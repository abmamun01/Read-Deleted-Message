package com.mamunsproject.deletedmessagereader_notification_reader.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NotificationViewModelFactory(private val repository: NotificationRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationVIewModel(repository) as T
    }
}
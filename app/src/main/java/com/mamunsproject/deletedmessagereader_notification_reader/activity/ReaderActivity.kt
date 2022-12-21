package com.mamunsproject.deletedmessagereader_notification_reader.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamunsproject.deletedmessagereader_notification_reader.R
import com.mamunsproject.deletedmessagereader_notification_reader.adapter.ReaderAdapter
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.*
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReaderActivity : AppCompatActivity(), ReaderAdapter.AppItemClickInterface {
    lateinit var notificationViewModel: NotificationVIewModel
    lateinit var appViewModel: AppViewModel
    lateinit var adapter: ReaderAdapter
    lateinit var list: ArrayList<NotificationEntity>

    lateinit var recyclerView: RecyclerView
    lateinit var packageNameFromPrevActivity: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reader)

        Log.d("KSLDFJLKSDFGFDD", "In On create:  From Reader ")

        recyclerView = findViewById(R.id.readerRecyclerViewID)
        recyclerView.layoutManager = LinearLayoutManager(this@ReaderActivity)
        recyclerView.setHasFixedSize(true)
        list = ArrayList()

        val appRepository = AppRepository(AppDatabase(this))
        val factory = ViewModelFactory(appRepository)
        appViewModel = ViewModelProvider(this, factory).get(AppViewModel::class.java)


        val notificationRepository = NotificationRepository(NotificationDatabase(this))
        val notificationFactory = NotificationViewModelFactory(notificationRepository)
        notificationViewModel =
            ViewModelProvider(this, notificationFactory).get(NotificationVIewModel::class.java)



        initi()


    }

    private fun initi() {
        Log.d("KSLDFJLKSDFGFDD", "Initi:  From Reader ")


        CoroutineScope(Dispatchers.IO).launch {

            packageNameFromPrevActivity = intent.getStringExtra("packageNameFromItem").toString()

            for (i in notificationViewModel.getAllNotification().indices) {
                Log.d(
                    "KSLDFJLKSDFGFDD",
                    "Pack Name from Intent $packageNameFromPrevActivity     from This ${
                        notificationViewModel.getAllNotification().get(i).app_name
                    }"
                )

                if (packageNameFromPrevActivity == notificationViewModel.getAllNotification()[i].package_name) {
                    var name = notificationViewModel.getAllNotification().get(i).app_name
                    var packageName = notificationViewModel.getAllNotification().get(i).package_name
                    var text = notificationViewModel.getAllNotification().get(i).text
                    var time = notificationViewModel.getAllNotification().get(i).timeWhenPosted
                    var date = notificationViewModel.getAllNotification().get(i).date
                    var imageIcon = notificationViewModel.getAllNotification().get(i).appImage
                    var model = NotificationEntity(text, packageName, name, time, date, imageIcon)
                    list.add(model)
                }
            }

            runOnUiThread {
                adapter = ReaderAdapter(list, this@ReaderActivity)
                recyclerView.adapter = adapter
            }
        }


    }


    override suspend fun onItemClick(groceryItem: NotificationEntity) {
        runOnUiThread {
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show()

        }
    }


}
package com.mamunsproject.deletedmessagereader_notification_reader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamunsproject.deletedmessagereader_notification_reader.activity.AppSelectionActivity
import com.mamunsproject.deletedmessagereader_notification_reader.adapter.AppAdapter
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.*
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationEntity
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), AppAdapter.AppItemClickInterface {


    lateinit var appViewModel: AppViewModel
    lateinit var notificationViewModel: NotificationVIewModel
    lateinit var adapter: AppAdapter
    lateinit var list: ArrayList<AppModel>
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var receiver: MyBroadCastRecievier = MyBroadCastRecievier()
        var filter: IntentFilter = IntentFilter()
        filter.addAction("com.example.notification_text")
        registerReceiver(receiver, filter)


        val appRepository = AppRepository(AppDatabase(this))
        val factory = ViewModelFactory(appRepository)
        appViewModel = ViewModelProvider(this, factory).get(AppViewModel::class.java)


        val notificationRepository = NotificationRepository(NotificationDatabase(this))
        val notificationFactory = NotificationViewModelFactory(notificationRepository)
        notificationViewModel =
            ViewModelProvider(this, notificationFactory).get(NotificationVIewModel::class.java)





        initialize()


        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            startActivity(Intent(this, AppSelectionActivity::class.java))
        }


    }

    inner class MyBroadCastRecievier : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var notificationText = intent!!.getStringExtra("notificationText")
            var notificationPackageName = intent.getStringExtra("notificationPackageName")
            var notificationTitle = intent.getStringExtra("notificationTitle")
            var notificationTime = intent.getIntExtra("notificationTime",0)
            var notificationDate = intent.getIntExtra("notificationDate",0)

           saveTheNotification(notificationPackageName!!,notificationText!!,notificationTime!!,notificationDate!!)


            Log.d(
                "NOtificatinxxx",
                "From Broad Cast *** : Text    $notificationText     timeOfNot   $notificationTime    Date   $notificationDate"
            )
        }

    }


    fun saveTheNotification(packageName: String, text: String, time: Int, date: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            for (i in appViewModel.getAllAppss().indices) {

                if (appViewModel.getAllAppss()[i].package_name == packageName) {

                    var textOfNotification = text
                    var timeOfNotification = time
                    var dateOfNotification = date

                    Log.d(
                        "NOtificatinxxx",
                        "saveTheNotification: Text    $textOfNotification     timeOfNot   $timeOfNotification    Date   $dateOfNotification"
                    )
                    var model = NotificationEntity(textOfNotification, timeOfNotification, dateOfNotification)
                    notificationViewModel.insert(model)

                }
            }

        }
    }

    private fun initialize() {

        CoroutineScope(Dispatchers.IO).launch {

            adapter = AppAdapter(appViewModel.getAllAppss(), this@MainActivity)

            recyclerView = findViewById(R.id.recyclerViewMain)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 3)

            recyclerView.adapter = adapter


        }


    }

    override suspend fun onItemClick(groceryItem: AppModel) {

    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}
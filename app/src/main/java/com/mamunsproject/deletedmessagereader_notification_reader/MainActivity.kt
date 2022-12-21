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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamunsproject.deletedmessagereader_notification_reader.activity.AppSelectionActivity
import com.mamunsproject.deletedmessagereader_notification_reader.activity.ReaderActivity
import com.mamunsproject.deletedmessagereader_notification_reader.adapter.AppAdapter
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.*
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationEntity
import kotlinx.android.synthetic.main.app_list_layout.*
import kotlinx.coroutines.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), AppAdapter.AppItemClickInterface {


    lateinit var appViewModel: AppViewModel
    lateinit var notificationViewModel: NotificationVIewModel
    lateinit var adapter: AppAdapter
    lateinit var list: ArrayList<AppModel>
    lateinit var recyclerView: RecyclerView
    lateinit var dateFormat: DateFormat
    lateinit var formattedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var receiver: MyBroadCastRecievier = MyBroadCastRecievier()
        var filter: IntentFilter = IntentFilter()
        filter.addAction("com.example.notification_text")
        registerReceiver(receiver, filter)

        recyclerView = findViewById(R.id.recyclerViewMain)


        //  dateFormat = SimpleDateFormat("hh:mm a")
        dateFormat = SimpleDateFormat("hh:mm a")
        formattedDate = dateFormat.format(Date()).toString()
        println(formattedDate)

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
            var notificationAppName = intent.getStringExtra("notificationAppName")
            var notificationPackageName = intent.getStringExtra("notificationPackageName")
            var notificationTitle = intent.getStringExtra("notificationTitle")
            var notificationTime = intent.getLongExtra("notificationTime", -1)
            var notificationDate = intent.getStringExtra("notificationDate")

            saveTheNotification(
                notificationPackageName!!,
                notificationTitle!!,
                notificationText!!,
                formattedDate,
                formattedDate
            )


            Log.d(
                "NOtificatinxxx",
                "From Broad Cast *** : Text    $notificationText     notificationDate   ${formattedDate}    notificationPackageName   $notificationPackageName notificationTitle   $notificationTitle notificationTime   $notificationTime"
            )
        }

    }


    fun saveTheNotification(
        packageName: String,
        appName: String,
        text: String,
        time: String,
        date: String
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            for (i in appViewModel.getAllAppss().indices) {

                if (appViewModel.getAllAppss()[i].package_name == packageName) {

                    var textOfNotification = text
                    var timeOfNotification = time
                    var dateOfNotification = date
                    var image = appViewModel.getAllAppss()[i].appImage
                    Log.d(
                        "NOtificatinxxx",
                        "saveTheNotification: Text    $textOfNotification     timeOfNot   $timeOfNotification    Date   $dateOfNotification"
                    )
                    var model = NotificationEntity(
                        textOfNotification,
                        packageName,
                        appName,
                        timeOfNotification,
                        dateOfNotification,
                        image
                    )
                    notificationViewModel.insert(model)

                }
            }

        }
    }

    private fun initialize() {

        MainScope().launch {
            withContext(Dispatchers.IO) {
                adapter = AppAdapter(appViewModel.getAllAppss(), this@MainActivity)



                runOnUiThread {
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 3)

                    recyclerView.adapter = adapter
                }

            }
        }


    }

    override suspend fun onItemClick(appItem: AppModel) {


        val intent = Intent(this@MainActivity, ReaderActivity::class.java)
        intent.putExtra("packageNameFromItem", appItem.package_name)
        startActivity(intent)
    }

}
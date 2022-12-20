package com.mamunsproject.deletedmessagereader_notification_reader

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mamunsproject.deletedmessagereader_notification_reader.activity.AppSelectionActivity
import com.mamunsproject.deletedmessagereader_notification_reader.adapter.AppAdapter
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.AppRepository
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.AppViewModel
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.ViewModelFactory
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), AppAdapter.AppItemClickInterface {


    lateinit var appViewModel: AppViewModel
    lateinit var adapter: AppAdapter
    lateinit var list: ArrayList<AppModel>
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val appRepository = AppRepository(AppDatabase(this))
        val factory = ViewModelFactory(appRepository)
        appViewModel = ViewModelProvider(this, factory).get(AppViewModel::class.java)




        initialize()


        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            startActivity(Intent(this, AppSelectionActivity::class.java))
        }


    }


    private fun initialize() {

        CoroutineScope(Dispatchers.IO).launch {
            adapter = AppAdapter(appViewModel.getAllAppss(), this@MainActivity)

            recyclerView = findViewById(R.id.recyclerViewMain)


            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager =
                GridLayoutManager(this@MainActivity, 3)


            recyclerView.adapter = adapter

        }



    }

    override suspend fun onItemClick(groceryItem: AppModel) {

    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}
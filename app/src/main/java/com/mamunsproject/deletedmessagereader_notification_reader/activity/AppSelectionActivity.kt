package com.mamunsproject.deletedmessagereader_notification_reader.activity

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamunsproject.deletedmessagereader_notification_reader.R
import com.mamunsproject.deletedmessagereader_notification_reader.adapter.AppAdapter
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.AppRepository
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.AppViewModel
import com.mamunsproject.deletedmessagereader_notification_reader.mvvm.ViewModelFactory
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppDatabase
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class AppSelectionActivity : AppCompatActivity(), AppAdapter.AppItemClickInterface {
    lateinit var list: ArrayList<AppModel>
    lateinit var adapter: AppAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var appViewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_selection)


        list = ArrayList()
        adapter = AppAdapter(list, this)
        lateinit var recyclerView: RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAppSelection)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        val appRepository = AppRepository(AppDatabase(this))
        val factory = ViewModelFactory(appRepository)
        appViewModel = ViewModelProvider(this, factory).get(AppViewModel::class.java)


        CoroutineScope(Dispatchers.Main).launch {
            val packageManager = packageManager
            val installedApps =
                packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

            for (i in installedApps.indices) {

                if (installedApps[i].flags and ApplicationInfo.FLAG_SYSTEM === ApplicationInfo.FLAG_SYSTEM) {
                    //system app
                } else {
                    //non system app

                    val appName = installedApps[i].loadLabel(packageManager).toString()
                    val packageName = installedApps[i].packageName
                    val label = installedApps[i].loadLabel(packageManager).toString()
                    val icon = installedApps[i].loadIcon(packageManager)
                    Log.d("InstalledAppsv", "$appName Pack Name  $packageName   Icon$icon")

                    var apps: AppModel =
                        AppModel(packageName, appName, drawableToByteArray(icon), false)

                    list.add(apps)

                }

            }
            recyclerView.adapter = adapter

        }


    }


    fun drawableToByteArray(drawable: Drawable): ByteArray {
        val bitmap = drawable.toBitmap()
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override suspend fun onItemClick(app: AppModel) {


/*        var id = app.id
        var isCheckeddd = app.isChecked

        if (isCheckeddd == true) {
            isCheckeddd = false
        } else {
            isCheckeddd = true
        }*/


        var appName = app.app_name
        var packageName = app.package_name
        var icon = app.appImage
        var isCheckeddd = app.isChecked
        isCheckeddd = true

        var appModel = AppModel(packageName, appName, icon, isCheckeddd)

        appViewModel.insert(appModel)


    }


}
package com.mamunsproject.deletedmessagereader_notification_reader.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mamunsproject.deletedmessagereader_notification_reader.R
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.AppModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppAdapter(
    var list: List<AppModel>, var appItemClickListener: AppItemClickInterface
) : RecyclerView.Adapter<AppAdapter.GroceryViewHolder>() {


    interface AppItemClickInterface {
        suspend fun onItemClick(groceryItem: AppModel)
    }

    class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTV = itemView.findViewById<TextView>(R.id.appName)
        val image = itemView.findViewById<ImageView>(R.id.appImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        Log.d("KSLDFJLKSDF", "onCreateViewHolder: ")

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.app_list_layout, parent, false)
        return GroceryViewHolder(view)


    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
/*        holder.nameTV.text = list.get(position).itemName
        holder.quantityTV.text = list.get(position).itemQuantity.toString()
        holder.rateTV.text = list.get(position).itemPrice.toString()
        val itemTotal: Int = list.get(position).itemPrice + list.get(position).itemQuantity


        val imageInByte = list.get(position).addImage
        val bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.size)

        holder.image.setImageBitmap(bitmap)


        holder.amountTV.text = "Tk. $itemTotal"*/


        holder.nameTV.text = list.get(position).app_name

        Log.d("KSLDFJLKSDF", "onBindViewHolder: ")
        val imageInByte = list.get(position).appImage
        val bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.size)

        holder.image.setImageBitmap(bitmap)


        holder.image.setOnClickListener {
            GlobalScope.launch {
                appItemClickListener.onItemClick(list.get(position))
            }
        }

    }

    override fun getItemCount(): Int {
        Log.d("KSLDFJLKSDF", "getItemCount: ${list.size}")

        return list.size
    }
}
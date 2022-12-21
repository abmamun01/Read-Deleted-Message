package com.mamunsproject.deletedmessagereader_notification_reader.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mamunsproject.deletedmessagereader_notification_reader.R
import com.mamunsproject.deletedmessagereader_notification_reader.room_db.NotificationEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReaderAdapter(var list: List<NotificationEntity>, var appItemClickListener: AppItemClickInterface) : RecyclerView.Adapter<ReaderAdapter.ReaderViewHolder>() {


    interface AppItemClickInterface {
        suspend fun onItemClick(groceryItem: NotificationEntity)
    }

    class ReaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textTV = itemView.findViewById<TextView>(R.id.textId)
        val date = itemView.findViewById<TextView>(R.id.timeText)
        val layout = itemView.findViewById<ConstraintLayout>(R.id.layoutID)
        val image = itemView.findViewById<ImageView>(R.id.icon_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReaderViewHolder {
        Log.d("KSLDFJLKSDFGFDD", "onCreateViewHolder:  From Reader ")

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.message_read_layout, parent, false)
        return ReaderViewHolder(view)


    }

    override fun onBindViewHolder(holder: ReaderViewHolder, position: Int) {
/*        holder.nameTV.text = list.get(position).itemName
        holder.quantityTV.text = list.get(position).itemQuantity.toString()
        holder.rateTV.text = list.get(position).itemPrice.toString()
        val itemTotal: Int = list.get(position).itemPrice + list.get(position).itemQuantity


        val imageInByte = list.get(position).addImage
        val bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.size)

        holder.image.setImageBitmap(bitmap)


        holder.amountTV.text = "Tk. $itemTotal"*/


        holder.textTV.text = list.get(position).text
        holder.date.text = list.get(position).date.toString()


        val imageInByte = list.get(position).appImage
        val bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.size)

        holder.image.setImageBitmap(bitmap)
        Log.d("KSLDFJLKSDFGFDD", "OnBind:  From Reader ")


        holder.layout.setOnClickListener {
            GlobalScope.launch {
                appItemClickListener.onItemClick(list.get(position))
            }
        }

    }

    override fun getItemCount(): Int {
        Log.d("KSLDFJLKSDFGFDD", "getItemCount From Reader : ${list.size}")

        return list.size
    }
}
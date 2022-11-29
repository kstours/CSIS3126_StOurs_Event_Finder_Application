package com.kyle.csis3126_stours

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class MyAdapter(private val eventList:ArrayList<eventData>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){





    class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val eventName : TextView = itemView.findViewById(R.id.textEventName)
        val eventDescription : TextView = itemView.findViewById(R.id.textEventDescription)
        val eventPicture : ImageView = itemView.findViewById(R.id.imageEventPic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val currentItem = eventList[position]
        val storageRef = FirebaseStorage.getInstance().getReference("images/${currentItem.name}")
        val localFile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            holder.eventPicture.setImageBitmap(bitmap)
        }

        holder.eventName.text = currentItem.name
        holder.eventDescription.text = currentItem.description

    }

    override fun getItemCount(): Int {

        return eventList.size

        }
}
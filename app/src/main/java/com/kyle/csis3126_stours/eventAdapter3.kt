package com.kyle.csis3126_stours

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class eventAdapter3(private val eventList: ArrayList<eventData>, private val ctx: Context) :
    RecyclerView.Adapter<eventAdapter3.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventName: TextView = itemView.findViewById(R.id.eventName3)
        val eventPicture: ImageView = itemView.findViewById(R.id.eventPic3)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDeleteEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_myevents, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = eventList[position]
        holder.setIsRecyclable(false);

        val storageRef = FirebaseStorage.getInstance()
            .getReference("images/events/${currentItem.name}+${currentItem.author}").downloadUrl.addOnSuccessListener {
            Picasso.get().load(it.toString()).into(holder.eventPicture)
            val temp = it.toString()
            holder.eventName.text = currentItem.name
            holder.buttonDelete.setOnClickListener {
                currentItem.name?.let { it1 -> Event.deleteEvent(User.username, User.token, it1) }
                val intent = Intent(ctx, ProfileActivity::class.java)
                Event.otherPeoplesEvents.clear()

                ctx.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return eventList.size
    }
}
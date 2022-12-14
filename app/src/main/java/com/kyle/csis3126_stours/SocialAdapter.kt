package com.kyle.csis3126_stours

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class SocialAdapter(private val users:ArrayList<String>, private val ctx:Context) : RecyclerView.Adapter<SocialAdapter.MyViewHolder>(){

    class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.eventName3)
        val pic : ImageView = itemView.findViewById(R.id.eventPic3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_profile,parent,false)
        return MyViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = users[position]
        holder.setIsRecyclable(false)
        FirebaseStorage.getInstance().getReference("images/users/${currentItem}ProfilePic").downloadUrl.addOnSuccessListener { it ->
            Picasso.get().load(it.toString()).into(holder.pic)
            holder.name.text = currentItem
            holder.itemView.setOnClickListener{
                val intent= Intent(ctx,UserProfileActivity::class.java)
                intent.putExtra("pic",it.toString())
                intent.putExtra("name",currentItem)
                ctx.startActivity(intent)
            }

        }

        }


    override fun getItemCount(): Int {
        return users.size
    }
}
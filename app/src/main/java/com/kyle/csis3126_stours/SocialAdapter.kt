package com.kyle.csis3126_stours

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class SocialAdapter(private val users: ArrayList<String>, private val ctx: Context) :
    RecyclerView.Adapter<SocialAdapter.MyViewHolder>() {
    private lateinit var databaseReference: DatabaseReference

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.eventName3)
        val pic: ImageView = itemView.findViewById(R.id.eventPic3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_profile, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = users[position]
        holder.setIsRecyclable(false)
        var good: Boolean = false
        var al: ArrayList<String> = ArrayList<String>()
        val default =
            "https://firebasestorage.googleapis.com/v0/b/csis3126-stours.appspot.com/o/images%2Fusers%2Fdefault.png?alt=media&token=e985d854-c14d-43a4-8246-f6e1771fda13"
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(currentItem).get().addOnSuccessListener { it ->
            if (it.exists()) {
                FirebaseStorage.getInstance()
                    .getReference("images/users/${currentItem}ProfilePic").downloadUrl.addOnSuccessListener { it ->
                        if (it != null) {
                            Picasso.get().load(it.toString()).into(holder.pic)
                            holder.name.text = currentItem
                            holder.itemView.setOnClickListener {
                                val intent = Intent(ctx, UserProfileActivity::class.java)
                                intent.putExtra("pic", it.toString())
                                intent.putExtra("name", currentItem)
                                ctx.startActivity(intent)
                            }
                        }

                    }.addOnFailureListener {
                        Picasso.get().load(default).into(holder.pic)
                        holder.name.text = currentItem
                        holder.itemView.setOnClickListener {
                            val intent = Intent(ctx, UserProfileActivity::class.java)
                            intent.putExtra("pic", it.toString())
                            intent.putExtra("name", currentItem)
                            ctx.startActivity(intent)
                        }
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}
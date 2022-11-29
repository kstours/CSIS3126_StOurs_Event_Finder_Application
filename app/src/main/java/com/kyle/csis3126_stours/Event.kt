package com.kyle.csis3126_stours

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.widget.Toast

import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.firebase.database.*

data class eventData (var author:String?, var name:String?,var description:String?,var date:String? )

class Event{
    private lateinit var databaseReference: DatabaseReference



    fun getEventData(arrayList: ArrayList<eventData>){
        databaseReference = FirebaseDatabase.getInstance().getReference("Events")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (eventSnapshot in snapshot.children){
                        val eventData = eventData(eventSnapshot.child("author").value.toString(),
                            eventSnapshot.child("name").value.toString(),
                            eventSnapshot.child("description").value.toString(),
                            eventSnapshot.child("date").value.toString())
                        arrayList.add(eventData!!)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }


    fun createEvent(name:String, description:String, date: String, author:String, context:Context){
        databaseReference = FirebaseDatabase.getInstance().getReference("Events")
        databaseReference.child(name).get().addOnSuccessListener {
            if(it.exists()){
                Toast.makeText(context,"Event name is already taken",Toast.LENGTH_SHORT).show()
            }else{
                val eventData = eventData(author,name,description,date)
                databaseReference.child(name).setValue(eventData)
                Toast.makeText(context,"Event Created!",Toast.LENGTH_SHORT).show()
                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra(author,"Username") //store username that way i can access data later
                context.startActivity(intent) //if its right  login

            }
    }


}

}
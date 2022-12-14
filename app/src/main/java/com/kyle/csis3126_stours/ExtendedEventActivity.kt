package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class ExtendedEventActivity : AppCompatActivity() {
    private lateinit var eventPic:ImageView
    private lateinit var eventName:TextView
    private lateinit var eventDesc:TextView
    private lateinit var eventAddr:TextView
    private lateinit var count:TextView
    private lateinit var eventState:TextView
    private lateinit var back:ImageView
    private lateinit var buttonAttending:Button
    private lateinit var dateT:TextView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extended_event)
        back = findViewById(R.id.imageBack)
        count = findViewById(R.id.textCount)
        eventPic= findViewById(R.id.imageEventImage)
        eventName = findViewById(R.id.textEventNameEX)
        eventDesc = findViewById(R.id.textEventDescEX)
        buttonAttending = findViewById(R.id.buttonRsvp)
        eventAddr = findViewById(R.id.textAddress)
        dateT = findViewById(R.id.textDate)
        eventState = findViewById(R.id.textState)
        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("desc")
        val date = intent.getStringExtra("data")
        val author = intent.getStringExtra("author")
        val state = intent.getStringExtra("state")
        val addy = intent.getStringExtra("addr")
        Picasso.get().load(intent.getStringExtra("image")).into(eventPic)
        eventName.text = name
        eventDesc.text = desc
        eventAddr.text = addy
        eventState.text = state
        dateT.text = date

        var attending:ArrayList<String> = intent.getStringArrayListExtra("attending") as ArrayList<String>


        if(attending.contains(User.username)){
            buttonAttending.text = "UNATTEND"
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Events")
        databaseReference.child(name.toString()).child("attending").get().addOnSuccessListener{
            count.text = (it.childrenCount.toInt()-1).toString()
        }




        back.setOnClickListener{


           finish()



        }

        buttonAttending.setOnClickListener {
            FirebaseStorage.getInstance().getReference("images/users/${User.username}ProfilePic").downloadUrl.addOnSuccessListener {
                databaseReference.child(name.toString()).child("attending").get().addOnSuccessListener{
                    if (name != null) {
                        Event.rsvpToEvent(name,User.username,buttonAttending,User.token,count)
                    }
                }
            }
        }












    }
}
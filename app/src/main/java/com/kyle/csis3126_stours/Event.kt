package com.kyle.csis3126_stours

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.kyle.csis3126_stours.User.removeAll
import java.lang.Thread.sleep

data class eventData(
    var author: String?,
    var name: String?,
    var description: String?,
    var date: String?,
    var state: String?,
    var addr: String?,
    var attending: ArrayList<String>,
    var interests: ArrayList<String>? = null
)

private lateinit var databaseReference: DatabaseReference
private lateinit var dbe: DatabaseReference

object Event {
    var allEvents: ArrayList<eventData> = ArrayList<eventData>()
    var nearEvents: ArrayList<eventData> = ArrayList<eventData>()
    var allOutdoor: ArrayList<eventData> = ArrayList<eventData>()
    var allKid: ArrayList<eventData> = ArrayList<eventData>()
    var allConcert: ArrayList<eventData> = ArrayList<eventData>()
    var allSports: ArrayList<eventData> = ArrayList<eventData>()
    var allFood: ArrayList<eventData> = ArrayList<eventData>()
    var allBusiness: ArrayList<eventData> = ArrayList<eventData>()
    var myEvents: ArrayList<eventData> = ArrayList<eventData>()
    var imAttending: ArrayList<eventData> = ArrayList<eventData>()
    var attending: ArrayList<String> = ArrayList<String>()
    var otherPeoplesEvents: ArrayList<eventData> = ArrayList<eventData>()


    fun sortTheirEvents(username: String) {
        if (allEvents.isNotEmpty() && otherPeoplesEvents.isEmpty()) {
            for (events in allEvents) {
                if (events.author == username) {
                    println("adding")
                    otherPeoplesEvents.add(events)
                }
            }
        } else if (allEvents.isNotEmpty()) {
            otherPeoplesEvents.clear()
            for (events in allEvents) {
                if (events.author == username) {
                    println("adding")
                    otherPeoplesEvents.add(events)
                }
            }
        }
    }


    fun refreshEvents() {
        allEvents.clear()
        nearEvents.clear()
        allOutdoor.clear()
        allKid.clear()
        allConcert.clear()
        allSports.clear()
        allFood.clear()
        allBusiness.clear()
        myEvents.clear()
        imAttending.clear()
        attending.clear()
        otherPeoplesEvents.clear()

        getEventData()
        sleep(200)
        sortEvents()
    }


    fun sortEvents() {
        if (allEvents.isNotEmpty() && imAttending.isEmpty() && myEvents.isEmpty()) { //these two kept duplicating in recycler views, maybe this is a quick fix
            for (events in allEvents) {
                for (i in events.attending) {
                    if (i == User.username) {
                        imAttending.add(events)
                    }
                }

                if (events.author == User.username) {
                    myEvents.add(events)
                }
                if (events.state == User.information["State"]) {
                    nearEvents.add(events)
                }
                for (interests in events.interests!!) {
                    if (interests.toString() == "Outdoors") {
                        allOutdoor.add(events)
                    }
                    if (interests.toString() == "Kid friendly") {
                        allKid.add(events)
                    }
                    if (interests.toString() == "Concerts") {
                        allConcert.add(events)
                    }
                    if (interests.toString() == "Food") {
                        allFood.add(events)
                    }
                    if (interests.toString() == "Business") {
                        allBusiness.add(events)
                    }
                    if (interests.toString() == "Sports") {
                        allSports.add(events)
                    }
                }
            }

        }
        println("business $allBusiness")
        println("Concert $allConcert")
        println("kid $allKid")
        println("Food $allFood")
        println("outdoor $allOutdoor")
        println("Sports $allSports")
        println("Near $nearEvents")
    }


    fun rsvpToEvent(
        eventName: String,
        username: String,
        button: Button,
        token: String,
        count: TextView
    ) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Events")
        dbe = FirebaseDatabase.getInstance().getReference("Users")
        dbe.child(User.username).get().addOnSuccessListener { it ->
            if (it.exists()) {
                dbe.child(User.username).child("token").get().addOnSuccessListener {
                    if (it.exists()) {
                        if (it.value.toString() == token) {
                            databaseReference.child(eventName).child("attending").get()
                                .addOnSuccessListener {
                                    if (it.exists()) {
                                        attending = it.value as ArrayList<String>
                                        if (!attending.contains(username)) {
                                            button.text = "Unattend"
                                            count.text =
                                                ((attending.size.toInt() + 1) - 1).toString() //database has a buffer of 1 space so table doesnt drop number is accurate this way
                                            attending.add(username)
                                            databaseReference.child(eventName).child("attending")
                                                .setValue(attending)
                                            attending.clear()
                                        } else {
                                            button.text = "Attend"
                                            count.text =
                                                ((attending.size.toInt() - 1) - 1).toString()
                                            attending.remove(username)
                                            databaseReference.child(eventName).child("attending")
                                                .setValue(attending)
                                            attending.clear()
                                        }
                                    }
                                }
                        } else {
                            println("Failed to verify token.")
                        }
                    }
                }
            }
        }
    }


    fun deleteEvent(username: String, token: String, eventName: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Events")
        dbe = FirebaseDatabase.getInstance().getReference("Users")
        dbe.child(User.username).get().addOnSuccessListener { it ->
            if (it.exists()) {
                dbe.child(User.username).child("token").get().addOnSuccessListener {
                    if (it.exists()) {
                        if (it.value.toString() == token) {
                            databaseReference.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        for (eventSnapshot in snapshot.children) {
                                            eventSnapshot.ref.get().addOnSuccessListener { res ->
                                                if (res.key == eventName) {
                                                    eventSnapshot.ref.removeValue()
                                                }
                                            }
                                        }

                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
                        }
                    }
                }
            }
        }


    }


    fun getEventData() {
        if (allEvents.isNotEmpty()) { // prevents it writing twice
            allEvents.clear()
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("Events")
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (eventSnapshot in snapshot.children) {
                            var attending =
                                eventSnapshot.child("attending").getValue<ArrayList<String>>()!!
                            var interests =
                                eventSnapshot.child("interests").getValue<ArrayList<String>>()!!
                            val eventData = eventData(
                                eventSnapshot.child("author").value.toString(),
                                eventSnapshot.child("name").value.toString(),
                                eventSnapshot.child("description").value.toString(),
                                eventSnapshot.child("date").value.toString(),
                                eventSnapshot.child("state").value.toString(),
                                eventSnapshot.child("addr").value.toString(),
                                attending,
                                interests
                            )
                            allEvents.add(eventData)
                            println(eventData)
                        }

                        sortEvents()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }


    }


    fun createEvent(
        name: String,
        description: String,
        date: String,
        author: String,
        state: String,
        attending: ArrayList<String>,
        tags: ArrayList<String>,
        addr: String,
        context: Context,
        token: String
    ) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Events")
        dbe = FirebaseDatabase.getInstance().getReference("Users")
        dbe.child(User.username).get().addOnSuccessListener { it ->
            if (it.exists()) {
                dbe.child(User.username).child("token").get().addOnSuccessListener {
                    if (it.exists()) {
                        if (it.value.toString() == token) {
                            databaseReference.child(name).get().addOnSuccessListener {
                                if (it.exists()) {
                                    Toast.makeText(
                                        context,
                                        "Event name is already taken",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                } else {
                                    val eventData = eventData(
                                        author,
                                        name,
                                        description,
                                        date,
                                        state,
                                        addr,
                                        attending,
                                        tags
                                    )
                                    databaseReference.child(name).setValue(eventData)
                                    println(state)
                                    Toast.makeText(context, "Event Created!", Toast.LENGTH_SHORT)
                                        .show()
                                    val intent = Intent(context, HomeActivity::class.java)
                                    intent.putExtra(
                                        author,
                                        "Username"
                                    ) //store username that way i can access data later
                                    myEvents.add(eventData)
                                    context.startActivity(intent)
                                }
                            }
                        }
                    } else {
                        println("Failed to verify token.")
                    }
                }
            }
        }
    }
}
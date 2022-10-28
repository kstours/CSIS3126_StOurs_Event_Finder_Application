package com.example.csis3126_stours
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class User(var firstName: String ?= null, var lastName : String ?= null, var agent: Int ?= null,var zipCode : Int ?= null,var tags: ArrayList<String>? = null)

class Event(var name: String,
            var description: String,
            val state: String,
            val city: String,
            val zipcode: Int,
            var price: Int,
            var tags: ArrayList<String>?)
{
    fun createEvent(){
        val db = Firebase.firestore
        val event = hashMapOf(
            "name" to name,
            "description" to description,
            "state" to state,
            "city" to city,
            "zipcode" to zipcode,
            "price" to price,
            "tags" to tags
        )//Creating hashmap of the data to send to firestore
        db.collection("events").add(event).addOnSuccessListener {
            println("Added Data")
        }
    }
    //testing only will change this later just want to make sure it works
    fun displayEvent(){
        val db = Firebase.firestore
        val event = db.collection("events").get()
        println(event)
    }

}
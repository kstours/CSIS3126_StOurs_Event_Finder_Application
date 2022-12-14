package com.kyle.csis3126_stours

import com.google.firebase.database.*

private lateinit var databaseReference: DatabaseReference

object firebaseData {
    var userList: ArrayList<String> = ArrayList<String>()
        private set





    fun getAllUsers() { //this seems to be called twice somew
        if (userList.isNotEmpty()){
            userList.clear()
        }else{
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (eventSnapshot in snapshot.children) {
                            println(eventSnapshot.key.toString())
                            userList.add(eventSnapshot.key.toString())
                        }

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }

    }



}
package com.example.csis3126_stours

import android.content.Intent
import android.os.Bundle
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class TwitterLoginActivity : RegisterActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        val provider = OAuthProvider.newBuilder("twitter.com")
        val pendingResultTask = auth.pendingAuthResult
        if (pendingResultTask != null) {
            pendingResultTask
                .addOnSuccessListener(
                    OnSuccessListener {
                        println("Signed in already")

                    })
                .addOnFailureListener {
                    println("line 28")
                }
        } else {
            auth
                .startActivityForSignInWithProvider( this, provider.build())
                .addOnSuccessListener {
                    startActivity(Intent(this,HomeActivity::class.java))
                }
                .addOnFailureListener {
                    //failing for some reason? will fix later, not worth setting myself back over this atm
                }
        }
    }



    private fun readData(userID : String?){
        db = FirebaseDatabase.getInstance().getReference("Users")
        if (userID != null) {
            db.child(userID).get().addOnSuccessListener {
                if(it.child("firstName").exists()){
                    println(it.child("firstName").value)
                    startActivity(Intent(this, HomeActivity::class.java))

                }else{
                    startActivity(Intent(this,GetInformationActivity::class.java))
                    //we get the data
                }
            }
        }

    }
}
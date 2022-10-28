package com.example.csis3126_stours

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.csis3126_stours.databinding.ActivityGetInformationBinding
import com.example.csis3126_stours.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityHomeBinding
    private lateinit var db : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tv1: TextView = findViewById(R.id.textView22)//for testing
        tv1.text = "Welcome:"
        auth = Firebase.auth
        db = FirebaseDatabase.getInstance().getReference("Users")
        var userID = auth.currentUser?.uid
        if (userID != null) {
            db.child(userID).get().addOnSuccessListener {
                tv1.text= "Welcome: ${it.child("firstName").value}" //works!
            }
        }

        binding.logOutButton.setOnClickListener {
            if(userID != null){
                //nullcheck even though you cant be on this page with out
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java)) //go back to main activity
            }
        }
    }



    ///BAD REPLACE THIS IN A CLASS!!!!!
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
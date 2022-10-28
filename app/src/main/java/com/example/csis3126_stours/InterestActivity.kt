package com.example.csis3126_stours

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.csis3126_stours.databinding.ActivityInterestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InterestActivity : GetInformationActivity() {
    private lateinit var binding: ActivityInterestBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : DatabaseReference
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInterestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var childFriendly = false
        var art = false
        var outDoors = false
        var food = false
        var sports = false
        var free  = false
        var tags = ArrayList<String>()




        //these buttons are a little weird, I need to fix them but they function for testing atm


        binding.childFriendly.setOnClickListener {
            if (!childFriendly){
                childFriendly = true
                binding.childFriendly.setBackgroundColor(R.color.green)
                println("true")
            }else{
                println("false")
                binding.childFriendly.setBackgroundColor(R.color.grey)
            }
        }

        binding.art.setOnClickListener {
            if (!art){
                art = true
                binding.art.setBackgroundColor(R.color.green)
                println("true")
            }else{
                println("false")
                binding.art.setBackgroundColor(R.color.grey)
            }
        }
        binding.outDoors.setOnClickListener {
            if (!outDoors){
                outDoors = true
                binding.outDoors.setBackgroundColor(R.color.green)
                println("true")
            }else{
                println("false")
                binding.outDoors.setBackgroundColor(R.color.grey)
            }
        }
        binding.food.setOnClickListener {
            if (!food){
                food = true
                binding.food.setBackgroundColor(R.color.green)
                println("true")
            }else{
                println("false")
                binding.food.setBackgroundColor(R.color.grey)
            }
        }
        binding.sports.setOnClickListener {
            if (!sports){
                sports = true
                binding.sports.setBackgroundColor(R.color.green)
                println("true")
            }else{
                println("false")
                binding.sports.setBackgroundColor(R.color.grey)
            }
        }
        binding.free.setOnClickListener {
            if (!free){
                free = true
                binding.free.setBackgroundColor(R.color.green)
                println("true")
            }else{
                println("false")
                binding.free.setBackgroundColor(R.color.grey)
            }
        }


        binding.continueButton.setOnClickListener {
            val extras = intent.extras

            val auth = FirebaseAuth.getInstance()
            val userID = auth.currentUser?.uid
            val firstName = extras?.getString("firstName")


            val lastName = extras?.getString("lastName")
            val age = extras?.getInt("age")
            val zipCode = extras?.getInt("zip")
            val db = FirebaseDatabase.getInstance().getReference("Users")
            val user = User(firstName, lastName,age,zipCode,tags)

            if(childFriendly){
                tags.add("Child Friendly")
            }
            if(art){
                tags.add("Art")
            }
            if(outDoors){
                tags.add("OutDoors")
            }
            if(food){
                tags.add("Food")
            }
            if(sports){
                tags.add("Sports")
            }
            if(free){
                tags.add("Free")
            }
            //probably a better way of adding to to the list but going to eep this for now

            if (userID != null) {
                println(tags)
                db.child(userID).setValue(user).addOnCompleteListener{
                    if (it.isSuccessful){
                        startActivity(Intent(this,HomeActivity::class.java))
                    }else{
                        println("Messed up") //handle error eventually
                    }
                }
            }

        }


    }
}


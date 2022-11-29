package com.kyle.csis3126_stours

import android.content.Context
import android.content.Intent
import android.widget.Toast

import at.favre.lib.crypto.bcrypt.BCrypt

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


data class UserData(var Username: String? = null, var Password: String? = null)

class User(){



    class Authentication(){
        private lateinit var databaseReference: DatabaseReference



        fun logIn(username: String,password: String,context: Context){ //login function
            databaseReference = FirebaseDatabase.getInstance().getReference("Users") //check if the username exists
            databaseReference.child(username).get().addOnSuccessListener{
                if(it.exists()){
                    databaseReference.child(username).child("password").get().addOnSuccessListener{it->
                        val result = BCrypt.verifyer().verify(password.toCharArray(),it.value.toString())
                        if(result.verified){
                            val intent = Intent(context, HomeActivity::class.java)
                            intent.putExtra("Username",username) //store username that way i can access data later
                            context.startActivity(intent) //if its right  login
                        }else{
                            Toast.makeText(context,"Invalid password!",Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        }



        fun signUp(username: String,password: String, confirmPassword: String,context:Context){
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
            databaseReference.child(username).get().addOnSuccessListener {
                if(it.exists()){//check if usernames taken
                        Toast.makeText(context,"Username is already taken",Toast.LENGTH_SHORT).show()
                }else{
                    if(password == confirmPassword){
                        val hashed:String = BCrypt.withDefaults().hashToString(12,password.toCharArray())//hashes password
                        val result = BCrypt.verifyer().verify(password.toCharArray(),hashed)//verifies before it stores
                        if(result.verified){
                            val userData = UserData(username,hashed)
                            databaseReference.child(username).setValue(userData)
                            val intent = Intent(context, HomeActivity::class.java)
                            intent.putExtra("Username",username) //store username that way i can access data later
                            context.startActivity(intent)
                        }
                    }
                    }
                }
            }
        }
}
package com.example.csis3126_stours

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.csis3126_stours.databinding.ActivityLoginBinding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid


        binding.backArrow.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


        binding.googleIcon.setOnClickListener {
            startActivity(Intent(this,GoogleLoginActivity::class.java)) //login with google
        }



        binding.twitterIcon.setOnClickListener {
            startActivity(Intent(this, TwitterLoginActivity::class.java)) //login with twitter
        }



        binding.facebookIcon.setOnClickListener { //functions but facebook isnt in live mode says i need to wait for my app to be reviewed?
            startActivity(Intent(this, FacebookLoginActivity::class.java))
        }

        binding.signInButton.setOnClickListener {
            val auth = Firebase.auth

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        readData(userID) //still need to check if the account has data -> user can open app make account then close before submitting their info
                    }else{
                        println("failed")//handle error
                    }
                }

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


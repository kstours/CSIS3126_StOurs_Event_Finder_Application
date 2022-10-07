package com.example.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.csis3126_stours.databinding.ActivityRegisterBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding:ActivityRegisterBinding
    var callbackManager = CallbackManager.Factory.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.backArrow.setOnClickListener {   //takes you back to the main page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        binding.facebookIcon.setOnClickListener {

            //todo make this work Library is already imported just need to implement it

        }

        binding.backArrow.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        binding.signUpButton.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    val hashed = BCrypt.withDefaults().hashToString(12, password.toCharArray()) //hashing the password using bcrypt (Should be ok from what I have seen)
                    var result = BCrypt.verifyer().verify(password.toCharArray(), hashed) //verify the hash
                    if (result.verified) {
                        auth.createUserWithEmailAndPassword(email, hashed)
                            .addOnCompleteListener { it -> //create the account on firebase
                                if (it.isSuccessful) {
                                    auth.signInWithEmailAndPassword(email, hashed)
                                        .addOnCompleteListener { //if the account is created just automatically login
                                            if (it.isSuccessful) {
                                                val intent = Intent(
                                                    this,
                                                    GetInformationActivity::class.java
                                                ) //direct to the information page
                                                startActivity(intent)
                                            } else {
                                                println(it.exception.toString())
                                            }
                                        }
                                }
                            }
                    } else { //otherwise print errors
                        Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                } //can be more specific with these thinking of adding direct highlighting on the invalid fields instead of using toasts
            }
        }
    }
}
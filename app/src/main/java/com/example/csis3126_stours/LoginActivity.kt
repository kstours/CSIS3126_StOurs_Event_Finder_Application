package com.example.csis3126_stours

import android.content.Intent


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.csis3126_stours.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()



        binding.backArrow.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


      binding.signInButton.setOnClickListener{
          val email = binding.email.text.toString()
          val password = binding.password.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                val hashed = BCrypt.withDefaults().hashToString(12,password.toCharArray()) //hashing the password using bcrypt (Should be ok from what I have seen)
                    auth.signInWithEmailAndPassword(email,hashed).addOnCompleteListener{ //firebsae also hashes the password on storeage could this be causing issues?
                        if (it.isSuccessful){
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        }
                }
           }
       }
    }
}
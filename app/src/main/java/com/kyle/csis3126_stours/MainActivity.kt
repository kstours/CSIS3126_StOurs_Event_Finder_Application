package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var textButtonSignUp: TextView
    lateinit var buttonLogin: Button
    lateinit var textUsername: EditText
    lateinit var textPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonLogin = findViewById(R.id.buttonLogin)
        textButtonSignUp = findViewById(R.id.textSignUpButton)
        textUsername = findViewById<EditText>(R.id.editTextUsername)
        textPassword = findViewById<EditText>(R.id.editTextPassword)
        val auth = User.Authentication()


        buttonLogin.setOnClickListener{
            auth.logIn(textUsername.text.toString(),textPassword.text.toString(),this)

        }

        textButtonSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
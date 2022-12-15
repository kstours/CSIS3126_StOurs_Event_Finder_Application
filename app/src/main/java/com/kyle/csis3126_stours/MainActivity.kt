package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.kyle.csis3126_stours.User.removeAll

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
        textUsername = findViewById(R.id.editTextUsername)
        textPassword = findViewById(R.id.editTextPassword)



        buttonLogin.setOnClickListener {


            if (textUsername.text.isEmpty()) {
                Toast.makeText(this, "Please input your Username!", Toast.LENGTH_SHORT).show()
            }
            if (textPassword.text.isEmpty()) {
                Toast.makeText(this, "Please input your Password!", Toast.LENGTH_SHORT).show()
            }
            if (textPassword.text.isNotEmpty() && textUsername.text.isNotEmpty()) {
                val clean = textUsername.text.toString().removeAll(setOf('.','#','$','[',']'))

                User.logIn(clean, textPassword.text.toString(), this)
            }
        }

        textButtonSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
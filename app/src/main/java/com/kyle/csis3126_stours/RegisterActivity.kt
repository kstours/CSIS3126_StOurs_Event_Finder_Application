package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.kyle.csis3126_stours.User.removeAll

class RegisterActivity : AppCompatActivity() {
    lateinit var textUsername: EditText
    lateinit var textPassword: EditText
    lateinit var textConfirmPassword: EditText
    lateinit var buttonSignup: Button
    lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        back = findViewById(R.id.imageBack2)
        textUsername = findViewById(R.id.editTextUsername)
        textPassword = findViewById(R.id.editTextPassword)
        textConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonSignup = findViewById(R.id.buttonSignUp)


        back.setOnClickListener {
            finish()
        }
        buttonSignup.setOnClickListener {
            if (textUsername.text.toString().isEmpty() || textPassword.text.toString()
                    .isEmpty() || textConfirmPassword.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Please fill out all fields!", Toast.LENGTH_SHORT).show()
            } else {
                val clean = textUsername.text.toString().removeAll(setOf('.','#','$','[',']'))
                User.signUp(
                    clean,
                    textPassword.text.toString(),
                    textConfirmPassword.text.toString(),
                    this
                )
            }
        }

    }
}
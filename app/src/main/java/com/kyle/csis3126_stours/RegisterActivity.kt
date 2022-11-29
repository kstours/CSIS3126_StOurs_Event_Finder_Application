package com.kyle.csis3126_stours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {
    lateinit var textUsername: EditText
    lateinit var textPassword: EditText
    lateinit var textConfirmPassword: EditText
    lateinit var buttonSignup:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        textUsername = findViewById<EditText>(R.id.editTextUsername)
        textPassword = findViewById<EditText>(R.id.editTextPassword)
        textConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        buttonSignup = findViewById<Button>(R.id.buttonSignUp)

        val auth = User.Authentication()


        //sign up button
        buttonSignup.setOnClickListener{
            if(textUsername.text.toString().isEmpty() || textPassword.text.toString().isEmpty() || textConfirmPassword.text.toString().isEmpty()){
                Toast.makeText(this,"Please fill out all fields!",Toast.LENGTH_SHORT).show()
            }else{
                auth.signUp(textUsername.text.toString(),textPassword.text.toString(),textConfirmPassword.text.toString(),this)
            }
        }

    }
}
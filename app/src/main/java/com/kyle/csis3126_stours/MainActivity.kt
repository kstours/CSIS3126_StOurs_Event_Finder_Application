package com.kyle.csis3126_stours

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.kyle.csis3126_stours.User.removeAll

class MainActivity : AppCompatActivity() {

    lateinit var textButtonSignUp: TextView
    lateinit var buttonLogin: Button
    lateinit var textUsername: EditText
    lateinit var textPassword: EditText
    lateinit var switch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonLogin = findViewById(R.id.buttonLogin)
        textButtonSignUp = findViewById(R.id.textSignUpButton)
        textUsername = findViewById(R.id.editTextUsername)
        textPassword = findViewById(R.id.editTextPassword)
        switch = findViewById(R.id.switch1)


        loadInfo()


        buttonLogin.setOnClickListener {

            saveInfo()
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
            saveInfo()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun saveInfo(){
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("switch_key",switch.isChecked)
            putString("user_key",textUsername.text.toString())
            putString("password_key",textPassword.text.toString())
        }.apply()

    }
    private fun loadInfo(){
        val sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val savedUser = sharedPreferences.getString("user_key",null)
        val savedSwitch = sharedPreferences.getBoolean("switch_key",false)
        val savedPassword = sharedPreferences.getString("password_key",null)
        if(savedSwitch){
            textUsername.setText(savedUser)
            switch.isChecked = true
            textPassword.setText(savedPassword)
        }
    }

}
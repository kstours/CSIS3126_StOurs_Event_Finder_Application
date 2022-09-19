package com.example.csis3126_stours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signUp = findViewById<Button>(R.id.signUpButton)
            signUp.setOnClickListener{
                println("BUtton Listener Works")
            }

    }
}
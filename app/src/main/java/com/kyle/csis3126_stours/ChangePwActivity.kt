package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.sax.StartElementListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class ChangePwActivity : AppCompatActivity() {
    lateinit var textNewPW: EditText
    lateinit var textCPassword: EditText
    lateinit var textConfirmPassword: EditText
    lateinit var buttonSignup: Button
    lateinit var back: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pw)

        textNewPW = findViewById(R.id.editnewPW)
        textCPassword = findViewById(R.id.editCurrentPW)
        textConfirmPassword = findViewById(R.id.confirmNewPW)
        buttonSignup = findViewById(R.id.buttonChangePw)
        back = findViewById(R.id.imageBack3)




        back.setOnClickListener {
            finish()
        }

        buttonSignup.setOnClickListener {
            User.changePassword(
                User.username,
                textCPassword.text.toString(),
                textNewPW.text.toString(),
                textConfirmPassword.text.toString(),
                User.token,
                this
            )
        }


    }
}



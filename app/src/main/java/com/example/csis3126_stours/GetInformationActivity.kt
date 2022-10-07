package com.example.csis3126_stours

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.csis3126_stours.databinding.ActivityGetInformationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class GetInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetInformationBinding
    private lateinit var auth: FirebaseAuth





    // crashing when coming here fix this later just rough design right now problem with the xml layout probably

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_information)
        auth = FirebaseAuth.getInstance()


        binding.continueButton.setOnClickListener{
            val firstname = binding.firstname.text.toString()
            val lastname = binding.lastname.text.toString()

            if(TextUtils.isEmpty(firstname)){
                Toast.makeText(this, "First name required", Toast.LENGTH_SHORT).show() //again toasts are going to be bad and annoying changing this later
                return@setOnClickListener
            }

            if(TextUtils.isEmpty(lastname)){
                Toast.makeText(this, "Last name required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //if these are both filled we can add to database
            var db = FirebaseFirestore.getInstance()
            val user: MutableMap<String, Any> = HashMap()
            user["first"] = firstname
            user["last"] = lastname
            db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
        }
    }
}
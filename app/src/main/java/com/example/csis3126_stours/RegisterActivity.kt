package com.example.csis3126_stours
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.csis3126_stours.databinding.ActivityRegisterBinding
import com.facebook.CallbackManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


open class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding
    var callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backArrow.setOnClickListener {   //takes you back to the main page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        //for signup
        binding.signUpButton.setOnClickListener {
            auth = Firebase.auth
            val db = Firebase.firestore
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.confirmPassword.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            println("Account Created")
                            var id = auth.currentUser.toString()
                            startActivity(Intent(this,GetInformationActivity::class.java))
                        } else {
                            println("Error")
                        }
                    }

                }
            } else {
                Toast.makeText(applicationContext, "Fill In all fields", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
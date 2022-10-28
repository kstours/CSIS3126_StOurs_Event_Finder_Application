package com.example.csis3126_stours
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.csis3126_stours.databinding.ActivityGetInformationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

open class GetInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGetInformationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var auth = FirebaseAuth.getInstance()

        binding.continueButton.setOnClickListener {

            if(binding.yourAge.toString().isNotEmpty()){//seems to always be not empty>
                val i = Intent(this,InterestActivity::class.java)
                i.putExtra("firstName",binding.yourFirstName.text.toString().trim())
                i.putExtra("lastName",binding.yourLastName.text.toString().trim())
                i.putExtra("age",Integer.parseInt(binding.yourAge.text.toString().trim()))
                i.putExtra("zip",Integer.parseInt(binding.yourZipcode.text.toString().trim())) //to access across other activity
                startActivity(i)
            }else{
                println("needs to fill all fields")
            }
        }
}

}



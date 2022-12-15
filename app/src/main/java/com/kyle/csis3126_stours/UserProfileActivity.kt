package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class UserProfileActivity : AppCompatActivity() {
    lateinit var listView: ListView
    lateinit var bottomNav: BottomNavigationView
    lateinit var profilePic: ImageView
    lateinit var back: ImageView
    lateinit var textName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        textName = findViewById(R.id.textProfile)
        bottomNav = findViewById(R.id.bottom_nav)
        profilePic = findViewById(R.id.imageProfilePic)
        bottomNav.selectedItemId = R.id.ic_users
        listView = findViewById(R.id.listViewProfile)
        back = findViewById(R.id.imageBack6)
        val values = arrayListOf("View their events")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values)
        listView.adapter = adapter
        FirebaseStorage.getInstance()
            .getReference("images/users/${intent.getStringExtra("name")}ProfilePic").downloadUrl.addOnSuccessListener { it ->
                Picasso.get().load(it.toString()).into(profilePic)
                textName.text = intent.getStringExtra("name")
                intent.putExtra("pic", it.toString())
            }
        listView.setOnItemClickListener { adapterView, view, i, l ->
            if (i == 0) {
                val name = intent.getStringExtra("name")
                if (name != null) {
                    Event.sortTheirEvents(name)
                }
                val intent = Intent(this, TheirEventsActivity::class.java)

                intent.putExtra("name", name.toString())
                startActivity(intent)
            }

        }
        back.setOnClickListener {
            val intent = Intent(this, SocialActivity::class.java)
            startActivity(intent)
        }

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    Event.refreshEvents()

                    finish()

                    startActivity(intent)

                }
                R.id.ic_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    Event.refreshEvents()

                    finish()

                    startActivity(intent)
                }

                R.id.ic_AddEvent -> {
                    val intent = Intent(this, AddEventActivity::class.java)
                    Event.refreshEvents()

                    finish()

                    startActivity(intent)

                }
            }
            true
        }
    }

}

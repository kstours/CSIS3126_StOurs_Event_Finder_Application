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

class ProfileActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var profilePic: ImageView
    lateinit var listView: ListView
    lateinit var textName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        textName = findViewById(R.id.textProfile)
        bottomNav = findViewById(R.id.bottom_nav)
        profilePic = findViewById(R.id.imageProfilePic)
        bottomNav.selectedItemId = R.id.ic_profile
        listView = findViewById(R.id.listViewProfile)
        val values = arrayListOf(
            "Events I'm attending",
            "Manage my events",
            "Change password",
            "Change profile picture",
            "Logout"
        )
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values)
        listView.adapter = adapter
        val default =
            "https://firebasestorage.googleapis.com/v0/b/csis3126-stours.appspot.com/o/images%2Fusers%2Fdefault.png?alt=media&token=e985d854-c14d-43a4-8246-f6e1771fda13"

        FirebaseStorage.getInstance()
            .getReference("images/users/${User.username}ProfilePic").downloadUrl.addOnSuccessListener {
            Picasso.get().load(it.toString()).into(profilePic)
        }.addOnFailureListener {
            Picasso.get().load(default).into(profilePic)
        }
        textName.text = "${User.information["firstName"]} ${User.information["lastName"]}"
        listView.setOnItemClickListener { adapterView, view, i, l ->
            if (i == 0) {

                val intent = Intent(this, attendingEventsActivity::class.java)
                startActivity(intent)
            }
            if (i == 1) {
                val intent = Intent(this, MyEventActivity::class.java)
                startActivity(intent)
            }
            if (i == 2) {
                val intent = Intent(this, ChangePwActivity::class.java)
                startActivity(intent)
            }
            if (i == 3) {
                val intent = Intent(this, ChangeProfilePicActivity::class.java)
                startActivity(intent)


            }
            if (i == 4) {

                User.logOut(User.token, User.username, this)

            }
        }

        Event.refreshEvents()

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    Event.refreshEvents()


                    startActivity(intent)

                }
                R.id.ic_users -> {
                    val intent = Intent(this, SocialActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    Event.refreshEvents()


                    startActivity(intent)
                }

                R.id.ic_AddEvent -> {
                    val intent = Intent(this, AddEventActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

                    Event.refreshEvents()

                    startActivity(intent)

                }
            }
            true
        }
    }

}
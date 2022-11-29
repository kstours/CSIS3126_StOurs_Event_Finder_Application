package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var buttonLogout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        buttonLogout = findViewById(R.id.buttonLogout)
        bottomNav = findViewById(R.id.bottom_nav)


        val username: String? = intent.getStringExtra("Username")//this should never be null



        buttonLogout.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)//we just dont keep the username anymore, works as a logout (probably not very secure)
        }

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home-> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("Username",username)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivity(intent)
                }
                R.id.ic_AddEvent-> {
                    val intent = Intent(this, AddEventActivity::class.java)
                    intent.putExtra("Username",username)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent)
                }
            }
            true
        }
    }

}
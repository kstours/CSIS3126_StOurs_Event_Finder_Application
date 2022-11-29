package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    private lateinit var eventRecyclerView: RecyclerView
    private  lateinit var eventArrayList: ArrayList<eventData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNav = findViewById(R.id.bottom_nav)

        val username: String? =  intent.getStringExtra("Username")

        display()

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_settings-> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    intent.putExtra("Username",username)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)
                }
                R.id.ic_AddEvent-> {
                    val intent = Intent(this, AddEventActivity::class.java)
                    intent.putExtra("Username",username)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

                    startActivity(intent)
                }
            }
            true
        }
    }

private fun display(){
    eventRecyclerView = findViewById(R.id.eventList)
    eventRecyclerView.layoutManager = LinearLayoutManager(this)
    eventRecyclerView.setHasFixedSize(true)
    eventArrayList = arrayListOf<eventData>()
    Event().getEventData(eventArrayList)
    eventRecyclerView.adapter = MyAdapter(eventArrayList)

}
}
package com.kyle.csis3126_stours

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var recyclerView2:RecyclerView
    private lateinit var recyclerView3:RecyclerView
    private lateinit var recyclerView4:RecyclerView
    private lateinit var recyclerView5:RecyclerView
    private lateinit var recyclerView6:RecyclerView
    private lateinit var recyclerView7:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recyclerView2 = findViewById(R.id.recycler2)
        recyclerView3 = findViewById(R.id.recyclerDelete)
        recyclerView4 = findViewById(R.id.recycler4)
        recyclerView5 = findViewById(R.id.recycler5)
        recyclerView6 = findViewById(R.id.recycler6)
        recyclerView7 = findViewById(R.id.recycler7)
        eventRecyclerView = findViewById(R.id.recyclerView)
        display()
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.selectedItemId = R.id.ic_home;
        checkInfo()//Check if person is full yregistered
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_profile-> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    finish()
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)

                }
                R.id.ic_users->{
                    finish()

                    val intent = Intent(this, SocialActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)


                    startActivity(intent)
                }

                R.id.ic_AddEvent-> {
                    finish()

                    val intent = Intent(this, AddEventActivity::class.java)

                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

                    startActivity(intent)

                }
            }
            true
        }
    }


    private fun checkInfo(){
        if(User.information["firstName"] == ""){
            val intent = Intent(this,RegisterInterestsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            finish()
            startActivity(intent)
        }
    }


    private fun display(){
        displayBusiness()
        displayNear()
        displayConcert()
        displayFood()
        displayOutdoors()
        displayBusiness()
        displaySports()
        displayKids()
    }


    private fun displayNear(){
    eventRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    eventRecyclerView.setHasFixedSize(true)
    eventRecyclerView.adapter = eventAdapter(Event.nearEvents,this)
    }
    private fun displaySports(){
        recyclerView4.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView4.setHasFixedSize(true)
        recyclerView4.adapter = eventAdapter(Event.allSports,this)
    }
    private fun displayOutdoors(){
        recyclerView2.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView2.setHasFixedSize(true)
        recyclerView2.adapter = eventAdapter(Event.allOutdoor,this)
    }
    private fun displayConcert(){
        recyclerView3.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView3.setHasFixedSize(true)
        recyclerView3.adapter = eventAdapter(Event.allConcert,this)
    }
    private fun displayFood(){
        recyclerView5.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView5.setHasFixedSize(true)
        recyclerView5.adapter = eventAdapter(Event.allFood,this)
    }
    private fun displayKids(){
        recyclerView6.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView6.setHasFixedSize(true)
        recyclerView6.adapter = eventAdapter(Event.allKid,this)
    }
    private fun displayBusiness(){
        recyclerView7.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recyclerView7.setHasFixedSize(true)
        recyclerView7.adapter = eventAdapter(Event.allBusiness,this)
    }

}
package com.kyle.csis3126_stours

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList

class SocialActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var search: SearchView
    lateinit var recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social)
        recycler = findViewById(R.id.recyclerUsers)

        search = findViewById(R.id.searchView2)
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.selectedItemId = R.id.ic_users;
        display()
        Event.refreshEvents()


        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filter(newText)
                }
                return true

            }


            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_profile-> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    finish()

                    startActivity(intent)
                }

                R.id.ic_AddEvent->{
                    val intent = Intent(this, AddEventActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    finish()

                    startActivity(intent)
                }


                R.id.ic_home-> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    finish()

                    startActivity(intent)

                }
            }
            true
        }
    }

    private fun filter(string:String){
        var array: ArrayList<String> = ArrayList<String>()
        array = firebaseData.userList.distinct().toList() as ArrayList<String>
        val filtered:ArrayList<String> =  ArrayList<String>()

        if(string != null){
            for (i in array){
                if (i.toString().toLowerCase(Locale.ROOT).contains(string)){
                    filtered.add(i)
                }
            }
        }
        if (filtered.isEmpty()){
            display()
        }else{
            recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
            recycler.setHasFixedSize(true)
            recycler.adapter = SocialAdapter(filtered,this)
        }
    }

    private fun display(){
        var array: ArrayList<String> = ArrayList<String>()
        array = firebaseData.userList.distinct().toList() as ArrayList<String>
        if(array.contains(User.username)){
            array.remove(User.username)
        }
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycler.setHasFixedSize(true)
        recycler.adapter = SocialAdapter(array,this)
    }
}

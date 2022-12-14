package com.kyle.csis3126_stours

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TheirEventsActivity : AppCompatActivity() {
    lateinit var back: ImageView
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_their_events)

        recycler = findViewById(R.id.recyclerMine)
        back = findViewById(R.id.imageBack4)




        display()
        back.setOnClickListener{
            finish()
        }


    }


    private fun display(){
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycler.setHasFixedSize(true)
        recycler.adapter = eventAdapter4(Event.otherPeoplesEvents,this)
    }
}




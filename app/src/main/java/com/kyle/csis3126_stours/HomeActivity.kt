package com.kyle.csis3126_stours

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var recyclerView4: RecyclerView
    private lateinit var recyclerView5: RecyclerView
    private lateinit var recyclerView6: RecyclerView
    private lateinit var recyclerView7: RecyclerView

    private lateinit var text2: TextView
    private lateinit var text3: TextView
    private lateinit var text4: TextView
    private lateinit var text5: TextView
    private lateinit var text6: TextView
    private lateinit var text7: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recyclerView2 = findViewById(R.id.recycler2)
        recyclerView3 = findViewById(R.id.recycler3)
        recyclerView4 = findViewById(R.id.recycler4)
        recyclerView5 = findViewById(R.id.recycler5)
        recyclerView6 = findViewById(R.id.recycler6)
        recyclerView7 = findViewById(R.id.recycler7)
        eventRecyclerView = findViewById(R.id.recyclerView)
        text2 = findViewById(R.id.text2)
        text3 = findViewById(R.id.text3)
        text4 = findViewById(R.id.text4)
        text5 = findViewById(R.id.text5)
        text6 = findViewById(R.id.text6)
        text7 = findViewById(R.id.text7)

        display()


        println(Event.nearEvents)
        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.selectedItemId = R.id.ic_home;
        checkInfo()//Check if person is full yregistered
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    finish()
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)

                }
                R.id.ic_users -> {
                    finish()

                    val intent = Intent(this, SocialActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)


                    startActivity(intent)
                }

                R.id.ic_AddEvent -> {
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


    private fun checkInfo() {
        if (User.information["firstName"] == "") {
            val intent = Intent(this, RegisterInterestsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            finish()
            startActivity(intent)
        }
    }


    private fun display() { //this is disgusting but it works.
        displayNear()
        val interests = User.interests
        var interestList: ArrayList<String> = ArrayList<String>()
        interestList.add("Food")
        interestList.add("Kid Friendly")
        interestList.add("Concerts")
        interestList.add("Business")
        interestList.add("Sports")
        interestList.add("Outdoors")

        if (interests != null) {
            for (i in interests) {
                if (interestList.contains(i)) {
                    interestList.remove(i)
                }
            }
            for (i in 0..6) {
                if (interests.size == 0) {
                    println("nothing")
                }

                if (interests.size == 6) {

                    if (i == 0) {
                        text2.text = interests[i]
                        display(recyclerView2, interests[i])
                    }
                    if (i == 1) {
                        text3.text = interests[i]
                        display(recyclerView3, interests[i])
                    }
                    if (i == 2) {
                        text4.text = interests[i]
                        display(recyclerView4, interests[i])
                    }
                    if (i == 3) {
                        text5.text = interests[i]
                        display(recyclerView5, interests[i])
                    }
                    if (i == 4) {
                        text6.text = interests[i]
                        display(recyclerView6, interests[i])
                    }
                    if (i == 5) {
                        text7.text = interests[i]
                        display(recyclerView7, interests[i])
                    }
                } else if (interests.size == 5) {
                    if (i == 0) {
                        text2.text = interests[i]
                        display(recyclerView2, interests[i])
                    }
                    if (i == 1) {
                        text3.text = interests[i]
                        display(recyclerView3, interests[i])
                    }
                    if (i == 2) {
                        text4.text = interests[i]
                        display(recyclerView4, interests[i])
                    }
                    if (i == 3) {
                        text5.text = interests[i]
                        display(recyclerView5, interests[i])
                    }
                    if (i == 4) {
                        text6.text = interests[i]
                        display(recyclerView6, interests[i])
                    }
                } else if (interests.size == 4) {
                    if (i == 0) {
                        text2.text = interests[i]
                        display(recyclerView2, interests[i])
                    }
                    if (i == 1) {
                        text3.text = interests[i]
                        display(recyclerView3, interests[i])
                    }
                    if (i == 2) {
                        text4.text = interests[i]
                        display(recyclerView4, interests[i])
                    }
                    if (i == 3) {
                        text5.text = interests[i]
                        display(recyclerView5, interests[i])
                    }

                } else if (interests.size == 3) {
                    if (i == 0) {
                        text2.text = interests[i]
                        display(recyclerView2, interests[i])
                    }
                    if (i == 1) {
                        text3.text = interests[i]
                        display(recyclerView3, interests[i])
                    }
                    if (i == 2) {
                        text4.text = interests[i]
                        display(recyclerView4, interests[i])
                    }
                } else if (interests.size == 2) {
                    if (i == 0) {
                        text2.text = interests[i]
                        display(recyclerView2, interests[i])
                    }
                    if (i == 1) {
                        text3.text = interests[i]
                        display(recyclerView3, interests[i])
                    }

                } else if (interests.size == 1) {
                    if (i == 0) {
                        text2.text = interests[i]
                        display(recyclerView2, interests[i])
                    }


                }
            }


            for (i in 0..6) {
                if (interestList.size == 6) {
                    if (i == 0) {
                        text7.text = interestList[i]
                        display(recyclerView7, interestList[i])
                    }
                    if (i == 1) {
                        text6.text = interests[i]
                        display(recyclerView6, interestList[i])
                    }
                    if (i == 2) {
                        text5.text = interestList[i]
                        display(recyclerView5, interestList[i])
                    }
                    if (i == 3) {
                        text4.text = interestList[i]
                        display(recyclerView4, interestList[i])
                    }
                    if (i == 4) {
                        text3.text = interestList[i]
                        display(recyclerView3, interestList[i])
                    }
                    if (i == 5) {
                        text2.text = interestList[i]
                        display(recyclerView2, interestList[i])
                    }
                } else if (interestList.size == 5) {
                    if (i == 0) {
                        text7.text = interestList[i]
                        display(recyclerView7, interestList[i])
                    }
                    if (i == 1) {
                        text6.text = interestList[i]
                        display(recyclerView6, interestList[i])
                    }
                    if (i == 2) {
                        text5.text = interestList[i]
                        display(recyclerView5, interestList[i])
                    }
                    if (i == 3) {
                        text4.text = interestList[i]
                        display(recyclerView4, interestList[i])
                    }
                    if (i == 4) {
                        text3.text = interestList[i]
                        display(recyclerView3, interestList[i])
                    }
                } else if (interestList.size == 4) {
                    if (i == 0) {
                        text7.text = interestList[i]
                        display(recyclerView7, interestList[i])
                    }
                    if (i == 1) {
                        text6.text = interestList[i]
                        display(recyclerView6, interestList[i])
                    }
                    if (i == 2) {
                        text5.text = interestList[i]
                        display(recyclerView5, interestList[i])
                    }
                    if (i == 3) {
                        text4.text = interestList[i]
                        display(recyclerView4, interestList[i])
                    }

                } else if (interestList.size == 3) {
                    if (i == 0) {
                        text7.text = interestList[i]
                        display(recyclerView7, interestList[i])
                    }
                    if (i == 1) {
                        text6.text = interestList[i]
                        display(recyclerView6, interestList[i])
                    }
                    if (i == 2) {
                        text5.text = interestList[i]
                        display(recyclerView5, interestList[i])
                    }
                } else if (interestList.size == 2) {
                    if (i == 0) {
                        text7.text = interestList[i]
                        display(recyclerView7, interestList[i])
                    }
                    if (i == 1) {
                        text6.text = interestList[i]
                        display(recyclerView6, interestList[i])
                    }

                } else if (interestList.size == 1) {
                    if (i == 0) {
                        text7.text = interestList[i]
                        display(recyclerView7, interestList[i])
                    }

                }
            }


        }

    }//this is disgusting but it works.


    private fun displayNear() {
        eventRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        eventRecyclerView.setHasFixedSize(true)
        eventRecyclerView.adapter = eventAdapter(Event.nearEvents, this)
    }

    private fun display(RecyclerView: RecyclerView, string: String) {
        RecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        RecyclerView.setHasFixedSize(true)
        if (string == "Food") {
            RecyclerView.adapter = eventAdapter(Event.allFood, this)
        } else if (string == "Concerts") {
            RecyclerView.adapter = eventAdapter(Event.allConcert, this)
        } else if (string == "Outdoors") {
            RecyclerView.adapter = eventAdapter(Event.allOutdoor, this)
        } else if (string == "Sports") {
            RecyclerView.adapter = eventAdapter(Event.allSports, this)
        } else if (string == "Kid Friendly") {
            RecyclerView.adapter = eventAdapter(Event.allKid, this)
        } else if (string == "Business") {
            RecyclerView.adapter = eventAdapter(Event.allBusiness, this)

        }
    }
}
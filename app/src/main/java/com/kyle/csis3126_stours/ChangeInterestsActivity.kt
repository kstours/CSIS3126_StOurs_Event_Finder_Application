package com.kyle.csis3126_stours

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.storage.FirebaseStorage
import java.lang.Thread.sleep

class ChangeInterestsActivity : AppCompatActivity() {
    lateinit var chipGroup: ChipGroup


    lateinit var buttonContinue: Button
    lateinit var textfirstName: TextView
    lateinit var textlastName: TextView
    lateinit var spinner: Spinner
    lateinit var imageButton:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_interests)
        var errors: HashMap<String, String> = HashMap<String, String>()
        var info: HashMap<String, String> = HashMap<String, String>()

        val items = resources.getStringArray(R.array.states)

        val spinnerAdapter =
            object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {

                override fun isEnabled(position: Int): Boolean {

                    return position != 0
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view: TextView =
                        super.getDropDownView(position, convertView, parent) as TextView
                    if (position == 0) {
                        view.setTextColor(Color.GRAY)
                    }
                    return view
                }

            }
        imageButton = findViewById(R.id.imageBack7)
        spinner = findViewById(R.id.spinnerStateUser)
        textfirstName = findViewById(R.id.editTextFirstName)
        textlastName = findViewById(R.id.editTextLastName)
        buttonContinue = findViewById(R.id.buttonRegContinue)
        chipGroup = findViewById(R.id.chipGroupInterests)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                errors["State"] = "Please select a state"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (value == items[0]) {
                    (view as TextView).setTextColor(Color.GRAY)
                } else {
                    info["State"] = value
                    errors.remove("State")

                }

            }

        }

        textfirstName.text = User.information["firstName"]
        textlastName.text = User.information["lastName"]
        if(User.information["State"] == "Maine"){
            spinner.setSelection(1)
        }else if(User.information["State"] == "Massachusetts"){
            spinner.setSelection(2)
        }else if(User.information["State"] == "Vermont"){
            spinner.setSelection(3)
        }else if(User.information["State"] == "Rhode Island"){
            spinner.setSelection(4)
        }else if(User.information["State"] == "New Hampshire"){
            spinner.setSelection(5)
        }else if(User.information["State"] == "Connectitcut"){
            spinner.setSelection(6)
        }



        buttonContinue.setOnClickListener {
            val ids = chipGroup.checkedChipIds
            val interestList: ArrayList<String> = ArrayList<String>()
            for (id in ids) run {
                val chip: Chip = chipGroup.findViewById(id)
                interestList.add(chip.text.toString())
            }


            if (textfirstName.text.isEmpty()) {
                errors["FirstName"] = "Please enter a valid First Name"
                Toast.makeText(this, errors["FirstName"], Toast.LENGTH_SHORT).show()
            } else {
                info["firstName"] = textfirstName.text.toString()
            }
            if (textlastName.text.isEmpty()) {
                errors["LastName"] = "Please enter a valid Last Name"
                Toast.makeText(this, errors["LastName"], Toast.LENGTH_SHORT).show()
            } else {
                info["lastName"] = textlastName.text.toString()
            }
            if (errors["State"] == "Please select a state" || errors["State"] == "a") {
                Toast.makeText(this, "Please select a state", Toast.LENGTH_SHORT).show()
            }
            if (interestList.isEmpty()) {
                errors["Interests"] = "Please select At least One Interest!"
                Toast.makeText(this, errors["Interests"], Toast.LENGTH_SHORT).show()
            }
            if (errors.isEmpty()) {

                User.setUserInformation(User.username, User.token, info, interestList)
                Event.refreshEvents()
                finish()

            } else {
                println(errors)
            }
            errors.clear()
        }


    }
}



package com.kyle.csis3126_stours

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddEventActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var imageEvent: ImageView
    lateinit var buttonGetImage: Button
    lateinit var buttonAddEvent: Button
    lateinit var spinner: Spinner
    lateinit var textAddress: EditText
    lateinit var textEventName: EditText
    lateinit var textEventDescription: EditText
    lateinit var textDate: EditText
    lateinit var chipGroup: ChipGroup


    lateinit var photo: Uri
    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)

        textAddress = findViewById(R.id.editTextAddress)
        textEventName = findViewById(R.id.editTextEventName)
        textEventDescription = findViewById(R.id.editTextEventDescription)
        textDate = findViewById(R.id.editTextDate)
        spinner = findViewById(R.id.spinnerStateEvent)

        buttonGetImage = findViewById(R.id.buttonuploadPicture)
        bottomNav = findViewById(R.id.bottom_nav)
        imageEvent = findViewById(R.id.imageEvent)
        buttonAddEvent = findViewById(R.id.buttonAddEvent)
        chipGroup = findViewById(R.id.chipGroupInterests)
        Event.refreshEvents()

        bottomNav.selectedItemId = R.id.ic_AddEvent;
        val items = resources.getStringArray(R.array.states)
        var errors: HashMap<String, String> = HashMap<String, String>()
        var state :String = ""
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
                    } else {

                    }
                    return view
                }

            }

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
                    state = value
                    println(state)
                }

            }

        }

        buttonAddEvent.setOnClickListener {

            val ids = chipGroup.checkedChipIds
            var tags: ArrayList<String> = ArrayList<String>()
            for (id in ids) run {
                val chip: Chip = chipGroup.findViewById(id)
                tags.add(chip.text.toString())
            }
            if (!::photo.isInitialized) {
                errors["ProfilePic"] = "Please Upload a picture"
            }
            if (tags.isEmpty()) {
                errors["Tags"] = "Please select At least One Tag!"
            }

            if (textEventName.text.isEmpty()) {
                errors["FirstName"] = "Please enter a valid First Name"
            }
            if (textEventDescription.text.isEmpty()) {
                errors["LastName"] = "Please enter a valid Last Name"
            }

            if (errors.isEmpty() && state != "") {
                val list: ArrayList<String> = ArrayList<String>()
                list.add("0")
                uploadImage()
                Event.createEvent(
                    textEventName.text.toString(),
                    textEventDescription.text.toString(),
                    textDate.text.toString(),
                    User.username,state, list ,tags, textAddress.text.toString(),this, User.token
                )

                finish()
            } else {
                println(errors)
            }
            errors.clear()
        }








        if (textEventName.text.isEmpty() || textEventDescription.text.isEmpty() || textDate.text.isEmpty()) {
            println("EmptyS")
        } else {

        }


        textDate.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    textDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }

        buttonGetImage.setOnClickListener{

            selectImage()
        }


        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_profile-> {
                    finish()

                    val intent = Intent(this, ProfileActivity::class.java)
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


                R.id.ic_home-> {
                    finish()

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    startActivity(intent)

                }
            }
            true
        }
    }


    private fun uploadImage(){
        val storageRef = FirebaseStorage.getInstance().getReference("images/events/${textEventName.text}+${User.username}")
        storageRef.putFile(photo).addOnSuccessListener {

        }
    }


    private fun selectImage(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            1)
        }else{
            val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,2)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            photo = data.data!!

            if(Build.VERSION.SDK_INT >= 28){
                val source = ImageDecoder.createSource(this.contentResolver, photo)
                bitmap = ImageDecoder.decodeBitmap(source)
                imageEvent.setImageBitmap(bitmap)
            }else{
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,photo)
                imageEvent.setImageBitmap(bitmap)

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
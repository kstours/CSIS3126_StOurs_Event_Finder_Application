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
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.storage.FirebaseStorage
import java.lang.Thread.sleep

class RegisterInterestsActivity : AppCompatActivity() {
    lateinit var chipGroup: ChipGroup


    lateinit var buttonContinue: Button
    lateinit var pictureButton: ImageView
    lateinit var textfirstName: TextView
    lateinit var textlastName: TextView
    lateinit var spinner: Spinner
    lateinit var photo: Uri
    lateinit var bitmap: Bitmap
    var imageSet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_interests)
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


        spinner = findViewById(R.id.spinnerStateUser)
        textfirstName = findViewById(R.id.editTextFirstName)
        textlastName = findViewById(R.id.editTextLastName)
        pictureButton = findViewById(R.id.imageProfile)
        buttonContinue = findViewById(R.id.buttonRegContinue)
        chipGroup = findViewById(R.id.chipGroupInterests)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        checkInfo()
        errors["State"] = "a"
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

        pictureButton.setOnClickListener {
            selectImage()
        }

        buttonContinue.setOnClickListener {
            val ids = chipGroup.checkedChipIds
            var interestList: ArrayList<String> = ArrayList<String>()
            for (id in ids) run {
                val chip: Chip = chipGroup.findViewById(id)
                interestList.add(chip.text.toString())
            }
            if (!::photo.isInitialized) {
                errors["ProfilePic"] = "Please Upload a picture"
                Toast.makeText(this, errors["ProfilePic"], Toast.LENGTH_SHORT).show()
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
                uploadImage()

            } else {
                println(errors)
            }
            errors.clear()
        }


    }

    private fun uploadImage() {

        val storageRef =
            FirebaseStorage.getInstance().getReference("images/users/${User.username}ProfilePic")
        storageRef.putFile(photo).addOnSuccessListener {
            val intent = Intent(this, HomeActivity::class.java)
            Event.refreshEvents()
            sleep(1500)
            startActivity(intent)
        }
    }

    private fun selectImage() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            photo = data.data!!

            if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(this.contentResolver, photo)
                bitmap = ImageDecoder.decodeBitmap(source)
                pictureButton.setImageBitmap(bitmap)
                imageSet = true
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, photo)
                pictureButton.setImageBitmap(bitmap)
                imageSet = true

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun checkInfo() {
        if (User.information["firstName"] != "") {
            val intent = Intent(this, HomeActivity::class.java)

            startActivity(intent)
            finish()
        }
    }

}

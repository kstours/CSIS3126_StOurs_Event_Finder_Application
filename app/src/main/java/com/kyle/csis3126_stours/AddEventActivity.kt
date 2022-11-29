package com.kyle.csis3126_stours

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.storage.FirebaseStorage

class AddEventActivity : AppCompatActivity() {
    lateinit var bottomNav: BottomNavigationView
    lateinit var imageEvent: ImageView
    lateinit var buttonGetImage: Button
    lateinit var buttonAddEvent: Button

    lateinit var textEventName:EditText
    lateinit var textEventDescription: EditText
    lateinit var textDate: EditText


    lateinit var photo:Uri
    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)


        textEventName = findViewById(R.id.editTextEventName)
        textEventDescription = findViewById(R.id.editTextEventDescription)
        textDate = findViewById(R.id.editTextDate)


        buttonGetImage = findViewById(R.id.buttonuploadPicture)
        bottomNav = findViewById(R.id.bottom_nav)
        imageEvent = findViewById(R.id.imageEvent)
        buttonAddEvent = findViewById(R.id.buttonAddEvent)

        val username = intent.getStringExtra("Username")

        buttonAddEvent.setOnClickListener {
            if(textEventName.text.isEmpty() || textEventDescription.text.isEmpty() || textDate.text.isEmpty()){
                println("EmptyS")
            }else{
                uploadImage()
                Event().createEvent(textEventName.text.toString(),
                    textEventDescription.text.toString(),
                    textDate.text.toString(),
                    username.toString(),this)
            }






        }

        buttonGetImage.setOnClickListener{

            selectImage()
        }


        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_settings-> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    intent.putExtra("Username",username)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivity(intent)
                }
                R.id.ic_home-> {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("Username",username)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivity(intent)
                }
            }
            true
        }
    }


    private fun uploadImage(){
        val storageRef = FirebaseStorage.getInstance().getReference("images/${textEventName.text}")
        storageRef.putFile(photo).addOnSuccessListener {
            Toast.makeText(this,"Uploaded!",Toast.LENGTH_SHORT).show()
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
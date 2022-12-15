package com.kyle.csis3126_stours

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.storage.FirebaseStorage

class ChangeProfilePicActivity : AppCompatActivity() {
    private lateinit var back: ImageView
    private lateinit var pictureButton: ImageView
    private lateinit var button: Button
    lateinit var photo: Uri
    lateinit var bitmap: Bitmap
    var imageSet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile_pic)
        back = findViewById(R.id.imageBack5)
        pictureButton = findViewById(R.id.imageViewChangePic)
        button = findViewById(R.id.buttonSubmitPfp)

        pictureButton.setOnClickListener {
            selectImage()
        }

        button.setOnClickListener {
            if (imageSet) {
                uploadImage()
            } else {
                Toast.makeText(this, "Please select a new picture!", Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun uploadImage() {
        val storageRef =
            FirebaseStorage.getInstance().getReference("images/users/${User.username}ProfilePic")
        storageRef.putFile(photo).addOnSuccessListener {
            finish()
            val intent = Intent(this, ProfileActivity::class.java)
            Toast.makeText(this, "Profile Picture Updated!", Toast.LENGTH_SHORT).show()
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
}
package com.example.csis3126_stours

import android.content.Intent
import android.os.Bundle
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class FacebookLoginActivity : RegisterActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db : DatabaseReference
    private val userID = auth.currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth


        LoginManager.getInstance().logInWithReadPermissions(this,listOf("public_profile","email"))
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }
            override fun onCancel() {
            }
            override fun onError(error: FacebookException) {
            }
        })
    }
    fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    readData(userID)
                } else {
                    // If sign in fails, display a message to the user.

                }
            }
    }
    ///BAD REPLACE THIS IN A CLASS!!!!!
    private fun readData(userID : String?){
        db = FirebaseDatabase.getInstance().getReference("Users")
        if (userID != null) {
            db.child(userID).get().addOnSuccessListener {
                if(it.child("firstName").exists()){
                    println(it.child("firstName").value)
                    startActivity(Intent(this, HomeActivity::class.java))

                }else{
                    startActivity(Intent(this,GetInformationActivity::class.java))
                    //we get the data
                }
            }
        }

    }




}


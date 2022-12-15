package com.kyle.csis3126_stours

import android.content.Context
import android.content.Intent
import android.widget.Toast

import at.favre.lib.crypto.bcrypt.BCrypt
import com.google.firebase.database.*

import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


data class UserData(
    var Username: String? = null,
    var Password: String? = null,
    var token: String? = null,
    var information: HashMap<String, String>? = null,
    var interests: ArrayList<String>? = null
)


private lateinit var databaseReference: DatabaseReference

open class Authentication {
    var token: String = ""
        private set


    internal fun generateToken(username: String) {
        token = UUID.randomUUID().toString().replace("-", "")
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(username).get().addOnSuccessListener {
            if (it.exists()) {
                databaseReference.child(username).child("token").setValue(token)
            } else {
                println("some error")
            }
        }
    }


    internal fun destroyToken(username: String, token: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(username).get().addOnSuccessListener { it ->
            if (it.exists()) {
                databaseReference.child(username).child("token").get().addOnSuccessListener {
                    if (it.exists()) {
                        if (it.value.toString() == token) {
                            databaseReference.child(username).child("token").setValue("")
                        } else {
                            println("Failed to destory token.")
                        }
                    }
                }
            }
        }
    }
}

object User : Authentication() {
    var username: String = ""
        private set
    var information: HashMap<String, String> = HashMap<String, String>()
        private set
    var interests: ArrayList<String>? = ArrayList<String>()
        private set


    fun setUserInformation(
        username: String,
        token: String,
        information: HashMap<String, String>,
        interests: ArrayList<String>
    ) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(username).get().addOnSuccessListener { it ->
            if (it.exists()) {
                databaseReference.child(username).child("token").get().addOnSuccessListener {
                    if (it.exists()) {
                        if (it.value.toString() == token) {
                            User.interests = interests
                            User.information = information
                            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                            databaseReference.child(username).child("information")
                                .setValue(information)
                            databaseReference.child(username).child("interests").setValue(interests)
                        } else {
                            println("Failed to verify token.")
                        }
                    }
                }
            }
        }
    }


    fun signUp(username: String, password: String, confirmPassword: String, context: Context) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(username).get().addOnSuccessListener {
            if (it.exists()) {//check if usernames taken
                Toast.makeText(context, "Username is already taken", Toast.LENGTH_SHORT).show()
            } else {
                if (password == confirmPassword) {
                    val hashed: String = BCrypt.withDefaults()
                        .hashToString(12, password.toCharArray())//hashes password
                    val result = BCrypt.verifyer()
                        .verify(password.toCharArray(), hashed)//verifies before it stores
                    if (result.verified) {
                        var hm: HashMap<String, String> = HashMap<String, String>()
                        hm["firstName"] = ""
                        hm["lastName"] = ""
                        hm["zipcode"] = ""
                        var al: ArrayList<String> = ArrayList<String>()
                        al.add("Outdoors") //gets deleted buffer so stuff doesnt break
                        val userData = UserData(username, hashed, "", hm, al)
                        databaseReference.child(username).setValue(userData)
                        logIn(username, password, context)
                    }
                } else {
                    Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    fun changePassword(
        username: String,
        password: String,
        newPw: String,
        confirmNewPW: String,
        token: String,
        context: Context
    ) {
        if (newPw == confirmNewPW) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
            databaseReference.child(username).get().addOnSuccessListener { it ->
                if (it.exists()) {
                    databaseReference.child(username).child("token").get().addOnSuccessListener {
                        if (it.exists()) {
                            if (it.value.toString() == token) {
                                databaseReference.child(username).child("password").get()
                                    .addOnSuccessListener {
                                        val result = BCrypt.verifyer()
                                            .verify(password.toCharArray(), it.value.toString())
                                        if (result.verified) {
                                            val hashed: String = BCrypt.withDefaults()
                                                .hashToString(
                                                    12,
                                                    newPw.toCharArray()
                                                )//hashes password
                                            val result2 = BCrypt.verifyer().verify(
                                                newPw.toCharArray(),
                                                hashed
                                            )//verifies before it stores
                                            databaseReference.child(username).child("password")
                                                .setValue(hashed)
                                            val intent =
                                                Intent(context, ProfileActivity::class.java)
                                            Toast.makeText(
                                                context,
                                                "Password Changed!",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            context.startActivity(intent) //if its right  login
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Invalid password!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "New password does not match!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }


                }
            }
        }
    }


    fun logIn(username: String, password: String, context: Context) { //login function

        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users") //check if the username exists
        databaseReference.child(username).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                firebaseData.getAllUsers()
                databaseReference.child(username).child("password").get()
                    .addOnSuccessListener { it ->
                        val result =
                            BCrypt.verifyer().verify(password.toCharArray(), it.value.toString())
                        if (result.verified) {
                            generateToken(username)
                            User.username =
                                username //store username that way i can access data later
                            val intent = Intent(context, HomeActivity::class.java)
                            Event.getEventData()
                            information = dataSnapshot.child("information")
                                .getValue<HashMap<String, String>>()!!
                            interests =
                                dataSnapshot.child("interests").getValue<ArrayList<String>>()!!
                            Event.refreshEvents()
                            context.startActivity(intent) //if its right  login
                        } else {
                            Toast.makeText(context, "Invalid password!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(context, "User not found!", Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun logOut(token: String, username: String, context: Context) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(username).child("token").get().addOnSuccessListener {
            if (it.exists()) {
                if (it.value == token) {
                    destroyToken(username, this.token)
                    User.username = ""
                    this.information.clear()
                    this.interests?.clear()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }
    fun String.removeAll(charactersToRemove: Set<Char>): String {
        return filterNot { charactersToRemove.contains(it) }
    }
}


package com.openclassrooms.realestatemanager.networking

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class UserRepository {

    private lateinit var auth: FirebaseAuth
    lateinit var userRepository: UserRepository
    val currentUser = auth.currentUser

    companion object {
        private var instance: UserRepository? = null

        fun getInstance() =
            instance?: synchronized(this) {
            instance?: UserRepository().also { instance = it}
        }
    }

    fun createUserWithEmailAndPassord(userEmail:String, userPassword:String,){

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener() { it ->
                if (it.isSuccessful){
                    val user = auth.currentUser
                    Log.e("user creat", "not success")

                }else{
                    Log.e("user creat", "not success")
                }


            }

    }




}
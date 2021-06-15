package com.openclassrooms.realestatemanager.networking

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository {

    private lateinit var auth: FirebaseAuth
    lateinit var userRepository: UserRepository


    //Trouvé dans un tuto
    companion object {
        private var instance: UserRepository? = null

        fun getInstance() =
            instance?: synchronized(this) {
            instance?: UserRepository().also { instance = it}
        }
    }

    fun firebaseAuthGetInstance(){

        auth = FirebaseAuth.getInstance()

    }



    fun createUserWithEmailAndPassord(userEmail:String, userPassword:String,){

        firebaseAuthGetInstance()
        CoroutineScope(Dispatchers.IO).launch {
            try {

                auth.createUserWithEmailAndPassword(userEmail, userPassword)

            }catch (e: Exception){
                Log.e("create with emailPass", e.message.toString())
            }
        }

    }




}
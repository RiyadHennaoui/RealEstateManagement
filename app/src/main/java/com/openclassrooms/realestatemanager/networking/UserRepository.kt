package com.openclassrooms.realestatemanager.networking

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()



    //return LiveData boolean
    fun createUserWithEmailAndPassord(userEmail:String, userPassword:String, displayName: String,
                                      photoUrl: String ): LiveData<Boolean>{
        var  userMutableState: MutableLiveData<Boolean> = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            try {

                auth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            it.result?.user?.let { user ->
//                                user.updateProfile()
                            }
                        }
                    }

            }catch (e: Exception){
                Log.e("create with emailPass", e.message.toString())
            }
        }

        return userMutableState

    }




}
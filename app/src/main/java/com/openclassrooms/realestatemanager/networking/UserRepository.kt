package com.openclassrooms.realestatemanager.networking

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()



    //return LiveData boolean
    fun createUserWithEmailAndPassord(userEmail:String, userPassword:String, userDisplayName: String,
                                      photoUrl: String ): LiveData<Boolean>{
        var  userMutableState: MutableLiveData<Boolean> = MutableLiveData()

        val profileUpdates = userProfileChangeRequest {
            displayName = userDisplayName
            if (photoUrl.isNotEmpty()){
                //TODO retrouver comment ajouter la bonne uri avec une photo dans le telphone
                photoUri = Uri.parse(photoUrl)
            }
            }

        CoroutineScope(Dispatchers.IO).launch {
            try {

                auth.createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            userMutableState.postValue(false)
                            it.result?.user?.let { user ->
                                user.updateProfile(profileUpdates)
                                    .addOnCompleteListener { updateProfile ->
                                        if (updateProfile.isSuccessful){
                                            userMutableState.postValue(true)
                                        } else {
                                            userMutableState.postValue(false)
                                        }

                                    }
                            }
                        }else {
                            userMutableState.postValue(false)
                        }
                    }

            }catch (e: Exception){
                Log.e("create with emailPass", e.message.toString())
            }
        }

        return userMutableState

    }


    fun singin(userEmail:String, userPassword:String): LiveData<Boolean>{

        //TODO gerer le cas où le mot de passe est erroné.
        var isLog: MutableLiveData<Boolean> = MutableLiveData()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            isLog.postValue(true)
                        }else{
                            isLog.postValue(false)
                            Log.e("task signin", task.exception.toString())
                        }
                    }
            }catch (e: Exception){
                Log.e("signin with emailPass", e.message.toString() + " > " + userPassword)
                Log.e("signin string", userPassword + "")
            }
        }

        return isLog
    }




}
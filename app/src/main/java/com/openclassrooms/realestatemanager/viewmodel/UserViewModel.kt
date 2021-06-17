package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.networking.UserRepository

class UserViewModel(private val userRepository: UserRepository)
    : ViewModel() {

    fun createUser(userEmail:String, userPassword:String, displayName: String, photoUrl: String) =
        userRepository.createUserWithEmailAndPassord(userEmail, userPassword, displayName, photoUrl)

}
package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.networking.UserRepository

class UserViewModel(private val userRepository: UserRepository)
    : ViewModel() {

    fun createUser(userEmail:String, userPassword:String) =
        userRepository.createUserWithEmailAndPassord(userEmail, userPassword)

}
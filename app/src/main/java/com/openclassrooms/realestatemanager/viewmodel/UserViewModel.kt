package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.repositories.UserRepository

class UserViewModel(private val userRepository: UserRepository)
    : ViewModel() {

    fun createUser(userEmail:String, userPassword:String, displayName: String, photoUrl: String) =
        userRepository.createUserWithEmailAndPassord(userEmail, userPassword, displayName, photoUrl)

    fun signIn(userEmail:String, userPassword:String) = userRepository.singin(userEmail, userPassword)

}
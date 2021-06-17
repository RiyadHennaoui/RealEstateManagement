package com.openclassrooms.realestatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.networking.UserRepository
import com.openclassrooms.realestatemanager.viewmodel.UserViewModel
import com.openclassrooms.realestatemanager.viewmodel.UserViewModelFactory

class SignUpActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val etEmail: EditText = findViewById(R.id.sign_up_activity_et_email)
        val etPassword: EditText = findViewById(R.id.sign_up_activity_et_password)
        val btnRegister: ImageButton = findViewById(R.id.sign_up_activity_btn_validate)

        val userRepository = UserRepository()
        val factory = UserViewModelFactory(userRepository)

        val userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        btnRegister.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            //TODO ajouter ce qu'il faut

            if (email.isNotEmpty() && password.isNotEmpty()){

                userViewModel.createUser(email, password, ).observe(this, Observer {
                    intentToMainActivity()
                })

            }

        }

    }

    fun intentToMainActivity(){

        startActivity(Intent(this, MainActivity::class.java))

    }
}
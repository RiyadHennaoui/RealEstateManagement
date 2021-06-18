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
        val etDisplayName: EditText = findViewById(R.id.sign_up_activity_et_name)
        val btnRegister: ImageButton = findViewById(R.id.sign_up_activity_btn_validate)

        val userRepository = UserRepository()
        val factory = UserViewModelFactory(userRepository)

        val userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        btnRegister.setOnClickListener {

            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val displayName = etDisplayName.text.toString()
            //TODO ajouter la gestion des photos e tcheck si l'email et le password sont bon

            when {
                !isEmailValid(email) -> etEmail.error = "Email not valid"
                password.length < 3 -> etPassword.error = "password can not empty or under 3 characters"
                isEmailValid(email) && password.isNotEmpty() && displayName.isNotEmpty() ->
                    if (email.isNotEmpty() && password.isNotEmpty() && displayName.isNotEmpty()) {

                        userViewModel.createUser(email, password, displayName, photoUrl = "")
                            .observe(this, {
                                if(it){
                                    intentToMainActivity()
                                }
                            })

                    }
            }


        }

    }

    private fun intentToMainActivity() {

        startActivity(Intent(this, MainActivity::class.java))

    }

    private fun isEmailValid(email: CharSequence): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}
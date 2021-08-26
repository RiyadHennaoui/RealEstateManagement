package com.openclassrooms.realestatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.databinding.ActivitySignInBinding
import com.openclassrooms.realestatemanager.databinding.ActivitySignUpBinding
import com.openclassrooms.realestatemanager.repositories.UserRepository
import com.openclassrooms.realestatemanager.viewmodel.UserViewModel
import com.openclassrooms.realestatemanager.viewmodel.UserViewModelFactory

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val userRepository = UserRepository()
        val factory = UserViewModelFactory(userRepository)
        val userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        binding.signUpActivityBtnValidate.setOnClickListener {

            val email = binding.signUpActivityEtEmail.text.toString()
            val password = binding.signUpActivityEtPassword.text.toString()
            val displayName = binding.signUpActivityEtName.text.toString()
            //TODO ajouter la gestion des photos e tcheck si l'email et le password sont bon

            when {
                !isEmailValid(email) -> binding.signUpActivityEtEmail.error = "Email not valid"
                password.length < 3 -> binding.signUpActivityEtPassword.error = "password can not empty or under 3 characters"
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
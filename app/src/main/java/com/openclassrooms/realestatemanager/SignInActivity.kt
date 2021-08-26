package com.openclassrooms.realestatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.databinding.ActivitySignInBinding
import com.openclassrooms.realestatemanager.repositories.UserRepository
import com.openclassrooms.realestatemanager.viewmodel.UserViewModel
import com.openclassrooms.realestatemanager.viewmodel.UserViewModelFactory

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userRepository = UserRepository()
        val factory = UserViewModelFactory(userRepository)
        val userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)


        binding.signInActivityTvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


        binding.btnSignInLogin.setOnClickListener {
            val email = binding.etSignInEmail.text.toString()
            val password = binding.etSignInPassword.text.toString()


            when {
                !isEmailValid(email) -> binding.etSignInEmail.error = "Email not valid"
                password.length < 3  -> binding.etSignInPassword.error = "password can not empty or under 3 characters"
                isEmailValid(email) && password.isNotEmpty() ->
                    userViewModel.signIn(email, password).observe(this, {


                        if(it){
                            intentToMainActivity()
                        }else if (email.isEmpty()){
                            Toast.makeText(this, "Email is empty", Toast.LENGTH_LONG).show()
                        }else if(password.isEmpty()){

                            Toast.makeText(this, "password is empty", Toast.LENGTH_LONG).show()
                        }

                    })

            }

//            Log.e("signin", password)
//            Log.e("signin", email)
//            if (email.isNotEmpty() && password.isNotEmpty()){
//                if (isEmailValid(email)){
//                    userViewModel.signIn(email, password).observe(this, {
//
//
//                        if(it){
//                            intentToMainActivity()
//                        }else if (email.isEmpty()){
//                            Toast.makeText(this, "Email is empty", Toast.LENGTH_LONG).show()
//                        }else if(password.isEmpty()){
//
//                            Toast.makeText(this, "password is empty", Toast.LENGTH_LONG).show()
//                        }
//
//                    })
//                }else {
//                    etSigninEmail.error = "Email not valid"
//                    Toast.makeText(this, "Email not valid", Toast.LENGTH_LONG).show()
//                }
//
//            }
//
        }




    }

    private fun intentToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    //TODO trouver comment la définir en static dans un fichier apart
    private fun isEmailValid(email: CharSequence): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}

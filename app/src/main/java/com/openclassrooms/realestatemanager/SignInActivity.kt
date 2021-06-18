package com.openclassrooms.realestatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.networking.UserRepository
import com.openclassrooms.realestatemanager.viewmodel.UserViewModel
import com.openclassrooms.realestatemanager.viewmodel.UserViewModelFactory

class SignInActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val userRepository = UserRepository()
        val factory = UserViewModelFactory(userRepository)
        val userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        val tvSignUp: TextView = findViewById(R.id.sign_in_activity_tv_sign_up)
        val etSigninEmail: EditText = findViewById(R.id.etSignInEmail)
        val etSigninPassword: EditText = findViewById(R.id.etSignInPassword)
        val btnSignInLogin: Button = findViewById(R.id.btnSignInLogin)


        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }




        btnSignInLogin.setOnClickListener {
            val email = etSigninEmail.text.toString()
            val password = etSigninPassword.text.toString()


            when {
                !isEmailValid(email) -> etSigninEmail.error = "Email not valid"
                password.length < 3  -> etSigninPassword.error = "password can not empty or under 3 characters"
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

    private fun isEmailValid(email: CharSequence): Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


}

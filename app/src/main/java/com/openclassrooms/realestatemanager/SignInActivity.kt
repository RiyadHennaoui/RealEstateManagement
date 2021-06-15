package com.openclassrooms.realestatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignInActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val tvSignUp: TextView = findViewById(R.id.sign_in_activity_tv_sign_up)


        tvSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }
}

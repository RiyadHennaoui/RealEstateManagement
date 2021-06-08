package com.openclassrooms.realestatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashScreen : AppCompatActivity() {
    private var realEstateLogo: ImageView? = null
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        auth = Firebase.auth


        realEstateLogo = findViewById(R.id.splash_screen_iv_logo)
        val animFadeIn: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        realEstateLogo!!.startAnimation(animFadeIn)

        animFadeIn.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {



            }

            override fun onAnimationEnd(p0: Animation?) {

                if(auth.currentUser != null){
                    intentToMainActivity()
                }else{
                    intentToSignInActivity()
                }


            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        } )


    }




    fun intentToMainActivity(){

        startActivity(Intent(this, MainActivity::class.java))

    }

    fun intentToSignInActivity(){

        startActivity(Intent(this, SignInActivity::class.java))

    }

}
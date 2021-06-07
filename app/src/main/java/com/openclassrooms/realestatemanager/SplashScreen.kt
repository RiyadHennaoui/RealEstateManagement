package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView


class SplashScreen : AppCompatActivity() {
    private var realEstateLogo: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        realEstateLogo = findViewById(R.id.splash_screen_iv_logo)
        val animFadeIn: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        realEstateLogo!!.startAnimation(animFadeIn)


    }
}
package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        //For disable middle item in bottom app bar
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false


        fab.setImageDrawable(resources.getDrawable(R.drawable.ic_add))

    }
}
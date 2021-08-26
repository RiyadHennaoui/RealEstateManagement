package com.openclassrooms.realestatemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding


    var bottomNavigationView: BottomNavigationView? = null
    var fab: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fab = binding.fab


        disableMiddleItemInButtomAppBar()
        configureBottomView()
        configureFab()

    }

    private fun disableMiddleItemInButtomAppBar() {
        bottomNavigationView!!.background = null
        bottomNavigationView!!.menu.getItem(2).isEnabled = false
    }

    private fun configureBottomView(){
        bottomNavigationView?.setOnNavigationItemSelectedListener { item -> updateButtons(item.itemId) }
    }

    private fun configureFab(){
        fab?.setOnClickListener { item -> updateButtons(item.id) }
    }

    private fun updateButtons(integer: Int): Boolean{
        when (integer){

            R.id.bottom_nav_list_properties -> openListProperties()
            R.id.bottom_nav_map -> openMap()
            R.id.fab -> openAddProperty()
            R.id.bottom_nav_search_property -> openSearch()
            R.id.bottom_nav_simulator -> openCreditSimulator()
        }
        return true
    }

    private fun openSearch() {
        Toast.makeText(this,"search", Toast.LENGTH_LONG).show()
//        TODO("Not yet implemented")
    }

    private fun openCreditSimulator() {
        TODO("Not yet implemented")
    }

    private fun openMap() {
        Toast.makeText(this,"openMap", Toast.LENGTH_LONG).show()
//        TODO("Not yet implemented")
    }

    private fun openListProperties() {
        TODO("Not yet implemented")
    }

    private fun openAddProperty() {
        startActivity(Intent(this, AddPropertyActivity::class.java))
    }
}
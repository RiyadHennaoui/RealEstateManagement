package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var bottomNavigationView: BottomNavigationView? = null
    var fab: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fab = findViewById(R.id.fab)

        //For disable middle item in bottom app bar
        bottomNavigationView!!.background = null
        bottomNavigationView!!.menu.getItem(2).isEnabled= false

        configureBottomView()
        configureFab()

    }

    private fun configureBottomView(){
        bottomNavigationView?.setOnNavigationItemSelectedListener { item -> updateButtons(item.itemId) }
    }

    private fun configureFab(){
        fab?.setOnClickListener { item -> updateButtons(item.id) }
    }

    private fun updateButtons(integer: Int): Boolean{
        when (integer){

            R.id.bottom_nav_add_property -> openAddProperty()
            R.id.bottom_nav_list_properties -> openListProperties()
            R.id.bottom_nav_map -> openMap()
            R.id.bottom_nav_simulator -> openCreditSimulator()
            R.id.fab -> openSearch()
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
        TODO("Not yet implemented")
    }
}
package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.openclassrooms.realestatemanager.fragments.AddPropertyFragment

class MainActivity : AppCompatActivity() {

    val fragmentManager = supportFragmentManager
    var bottomNavigationView: BottomNavigationView? = null
    var fab: FloatingActionButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        fab = findViewById(R.id.fab)


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
        val transaction = fragmentManager.beginTransaction()
        val addPropertyFragment = AddPropertyFragment()
        transaction.replace(R.id.mainContainer, addPropertyFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
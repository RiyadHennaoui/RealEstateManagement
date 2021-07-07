package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModelFactory
import org.w3c.dom.Text

class AddPropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        //For ViewModels
        propertyViewmodelConfig()

        //Views for property photos
        val ivPropertyImage: ImageView= findViewById(R.id.ivPropertyImage)
        val ivAddPropertyImage: ImageView = findViewById(R.id.ivAddPhotoProperty)

        //Edit text for price
        val etPropertyPrice: EditText = findViewById(R.id.etPropertyPrice)

        //Buttons for type of property
        val btnHouse: Button = findViewById(R.id.btnHouse)
        val btnManor: Button = findViewById(R.id.btnManor)
        val btnCastle: Button = findViewById(R.id.btnCastle)
        val btnDuplex: Button = findViewById(R.id.btnDuplex)
        val btnPenthouse: Button = findViewById(R.id.btnPenthouse)
        val btnLoft: Button = findViewById(R.id.btnLoft)

        //Views for area
        val tvAreaValue: TextView = findViewById(R.id.areaValue)
        val areaSeekBar: SeekBar = findViewById(R.id.areaSeekBar)

        //Views for numbers of rooms and type
        val etRoomsValue: EditText = findViewById(R.id.etRoomsValue)
        val etBedroomsValue: EditText = findViewById(R.id.etBedroomValue)
        val etBathroomsValue: EditText = findViewById(R.id.etBathroomsValue)

        //Views for dates
        val ivEntryDate: ImageView = findViewById(R.id.ivEntryDate)
        val tvEntryDate: TextView = findViewById(R.id.tvEntryDate)
        val ivSoldDate: ImageView = findViewById(R.id.ivSoldDate)
        val tvSoldDate: TextView = findViewById(R.id.tvSoldDate)

        //Views for address
        val ivPlace: ImageView = findViewById(R.id.ivPlace)
        val tvPlace: TextView = findViewById(R.id.tvAddress)

        //Views for Point of interest
        val ibStore: ImageButton = findViewById(R.id.ibStoreFront)
        val ibBus: ImageButton = findViewById(R.id.ibBus)
        val ibSchool: ImageButton = findViewById(R.id.ibSchool)
        val ibSquare: ImageButton = findViewById(R.id.ibTree)

        //View for create Property
        val btnCreate: Button = findViewById(R.id.btnCreate)

        //View for back in MainActivity
        val ivBack: ImageView = findViewById(R.id.ivAddPropertyBack)





    }

    private fun propertyViewmodelConfig() {
        val propertyRepository = PropertyRepository(PropertyDatabase.invoke(this))
        val factory = PropertyViewModelFactory(propertyRepository)
        val propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)
    }
}
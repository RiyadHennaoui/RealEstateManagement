package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModelFactory
import org.w3c.dom.Text

class AddPropertyActivity : AppCompatActivity() {

    lateinit var propertyPhoto: String
    var price: Int = 0
    lateinit var typeOfProperty: String
    var areaValue: Int = 0
    var numberOfRooms: Int = 0
    var numberOfBedooms: Int = 0
    var numberOfBathooms: Int = 0
    lateinit var entryDate: String
    lateinit var soldDate: String
    lateinit var addressOfProperty: String
    lateinit var pointsOfInterest: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        //For ViewModels
        val propertyRepository = PropertyRepository(PropertyDatabase.invoke(this))
        val factory = PropertyViewModelFactory(propertyRepository)
        val propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        //Views for property photos
        val ivPropertyImage: ImageView= findViewById(R.id.ivPropertyImage)
        val ivAddPropertyImage: ImageView = findViewById(R.id.ivAddPhotoProperty)

        //Edit text for price
        val etPropertyPrice: EditText = findViewById(R.id.etPropertyPrice)


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

        btnCreate.setOnClickListener {

            val newProperty = Property(
                id = 0,
                price = etPropertyPrice.text.toString().toInt(),
                type = "House",
                area = tvAreaValue.text.toString().toInt(),
                roomsNumber = etRoomsValue.text.toString().toInt(),
                bedRoomsNumber = etBedroomsValue.text.toString().toInt(),
                bathRoomsNumber = etBathroomsValue.text.toString().toInt(),
                description = "some desc",
                photoUrl = "Hey my address is here",
                address = tvPlace.text.toString(),
                pointOfInterest = "Square",
                entryDate = tvEntryDate.text.toString(),
                saleDate = "SoldDate",
                estateAgent = "Me"
            )


            propertyViewModel.upsert(newProperty)
        }



    }

    private fun propertyViewmodelConfig() {
        val propertyRepository = PropertyRepository(PropertyDatabase.invoke(this))
        val factory = PropertyViewModelFactory(propertyRepository)
        val propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)
    }





    private fun typeOfPropertyListenerBtn(){




    }
}
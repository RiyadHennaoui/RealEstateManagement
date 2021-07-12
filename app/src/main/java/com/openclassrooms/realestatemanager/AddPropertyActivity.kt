package com.openclassrooms.realestatemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModelFactory

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


        //Chips for type of property
        val chipGroupTypeOfProperty: ChipGroup = findViewById(R.id.chipGroupTypeOfProperty)
        val cpHouse: Chip = findViewById(R.id.chipHouse)
        val cpManor: Chip = findViewById(R.id.chipManor)
        val cpCastle: Chip = findViewById(R.id.chipCastle)
        val cpPenthouse: Chip = findViewById(R.id.chipPenthouse)
        val cpDuplex: Chip = findViewById(R.id.chipDuplex)
        val cpLoft: Chip = findViewById(R.id.chipLoft)



        //Views for area


        //Views for numbers of rooms and type


        //Views for dates


        //Views for address


        //Views for Point of interest
        val groupChipsPointsOfInterest: ChipGroup = findViewById(R.id.chipsGroupPointsOfInterest)
        val chipStops: Chip = findViewById(R.id.chipShops)
        val chipSchool: Chip = findViewById(R.id.chipSchool)
        val chipPark: Chip = findViewById(R.id.chipPark)
        val chipTransport: Chip = findViewById(R.id.chipTransport)


        //View for create Property
        val btnCreate: ImageButton = findViewById(R.id.btnCreate)

        //View for back in MainActivity


       btnCreate.setOnClickListener {
//
//            val selectedChipId = chipGroupTypeOfProperty.checkedChipId
//
//            val choiseInString = typeOfPropertyChoise(selectedChipId)
//
//            Log.e("Here", choiseInString)

//            val newProperty = Property(
//                id = 0,
//                price = etPropertyPrice.text.toString().toInt(),
//                type = "House",
//                area = tvAreaValue.text.toString().toInt(),
//                roomsNumber = etRoomsValue.text.toString().toInt(),
//                bedRoomsNumber = etBedroomsValue.text.toString().toInt(),
//                bathRoomsNumber = etBathroomsValue.text.toString().toInt(),
//                description = "some desc",
//                photoUrl = "Hey my address is here",
//                address = tvPlace.text.toString(),
//                pointOfInterest = "Square",
//                entryDate = tvEntryDate.text.toString(),
//                saleDate = "SoldDate",
//                estateAgent = "Me"
//            )
           val pointOfInterest = groupChipsPointsOfInterest.checkedChipIds
           Log.e("here", pointOfInterest.toString())
//
//           propertyViewModel.upsert(newProperty)
        }


        val pointOfInterest = groupChipsPointsOfInterest.checkedChipIds



    }

    private fun propertyViewmodelConfig() {
        val propertyRepository = PropertyRepository(PropertyDatabase.invoke(this))
        val factory = PropertyViewModelFactory(propertyRepository)
        val propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)
    }



    private fun typeOfPropertyChoise(chipId: Int): String{
        var userChoice = ""
        when(chipId){

            R.id.chipHouse -> userChoice = "House"
            R.id.chipManor -> userChoice = "Manor"
            R.id.chipCastle -> userChoice = "Castle"
            R.id.chipDuplex -> userChoice = "Duplex"
            R.id.chipPenthouse -> userChoice = "Penthouse"
            R.id.chipLoft -> userChoice = "Loft"


        }
        return userChoice
    }


}
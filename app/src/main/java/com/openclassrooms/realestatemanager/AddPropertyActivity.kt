package com.openclassrooms.realestatemanager

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModelFactory

class AddPropertyActivity : AppCompatActivity() {

    var uriPhoto: Uri? = null
    var propertyPhotos = arrayListOf<String>()
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
    var photoDescription = ""
    var photosList = arrayListOf<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        //For ViewModels
        val propertyRepository = PropertyRepository(PropertyDatabase.invoke(this))
        val factory = PropertyViewModelFactory(propertyRepository)
        val propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        //View for back in MainActivity
        val btnHome: ImageView = findViewById(R.id.ivHome)

        //Views for Pictures
        val addPhoto: ImageButton = findViewById(R.id.ibAddPhoto)
        val displayPhoto: ImageView = findViewById(R.id.ivPropertyPhotos)


        //Chips for type of property
        val chipGroupTypeOfProperty: ChipGroup = findViewById(R.id.chipGroupTypeOfProperty)
        val cpHouse: Chip = findViewById(R.id.chipHouse)
        val cpManor: Chip = findViewById(R.id.chipManor)
        val cpCastle: Chip = findViewById(R.id.chipCastle)
        val cpPenthouse: Chip = findViewById(R.id.chipPenthouse)
        val cpDuplex: Chip = findViewById(R.id.chipDuplex)
        val cpLoft: Chip = findViewById(R.id.chipLoft)

        //View for price
        val price: TextInputEditText = findViewById(R.id.tietPrice)

        //Views for area
        val areaValue: TextInputEditText = findViewById(R.id.tietArea)

        //Views for numbers of rooms and type
        val roomsValue: TextInputEditText = findViewById(R.id.tietRooms)
        val bedroomsValue: TextInputEditText = findViewById(R.id.tietBedrooms)
        val bathroomsValue: TextInputEditText = findViewById(R.id.tietBathrooms)

        //Views for dates
        val entryDate: TextInputEditText = findViewById(R.id.tietEntryDate)

        //Views for address
        val address: TextInputEditText = findViewById(R.id.tietAddress)
        val zipCode: TextInputEditText = findViewById(R.id.tietZipCode)
        val state: TextInputEditText = findViewById(R.id.tietState)

        //Views for Point of interest
        val groupChipsPointsOfInterest: ChipGroup = findViewById(R.id.chipsGroupPointsOfInterest)
        val chipStops: Chip = findViewById(R.id.chipShops)
        val chipSchool: Chip = findViewById(R.id.chipSchool)
        val chipPark: Chip = findViewById(R.id.chipPark)
        val chipTransport: Chip = findViewById(R.id.chipTransport)

        //View for description
        val description: TextInputEditText = findViewById(R.id.tietDescription)

        //View for create Property
        val btnCreate: ImageButton = findViewById(R.id.btnCreate)


        val addPhotoDialog = addPhotoAlertDialog()


        addPhoto.setOnClickListener {
            addPhotoDialog.show()
        }



        btnCreate.setOnClickListener {

            val selectedChipId = chipGroupTypeOfProperty.checkedChipId

            val userChooseInString = typeOfPropertyChoise(selectedChipId)

            val completeAddress =
                "${address.text.toString()}, ${zipCode.text.toString()}, ${state.text.toString()}"

            val checkedPointOfInterestIds = groupChipsPointsOfInterest.checkedChipIds



            Log.e("Here", userChooseInString)

//            val newProperty = Property(
//                id = 0,
//                price = price.text.toString().toInt(),
//                type = userChooseInString,
//                area = areaValue.text.toString().toInt(),
//                roomsNumber = roomsValue.text.toString().toInt(),
//                bedRoomsNumber = bedroomsValue.text.toString().toInt(),
//                bathRoomsNumber = bathroomsValue.text.toString().toInt(),
//                description = description.text.toString(),
//                address = completeAddress,
//                pointOfInterest = pointsOfInterestToString(checkedPointOfInterestIds),
//                entryDate = entryDate.text.toString(),
//                saleDate = "",
//                estateAgent = "Me"
//            )

            pointsOfInterestToString(checkedPointOfInterestIds)

            val pointOfInterest = groupChipsPointsOfInterest.checkedChipIds
            Log.e("here", "${pointsOfInterestToString(pointOfInterest)}")
//
//           propertyViewModel.upsert(newProperty)
        }


        val pointOfInterest = groupChipsPointsOfInterest.checkedChipIds


    }

    private fun addPhotoAlertDialog(): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Add Photo")
            .setMessage("Choose one in your gallery or take it with your camera")
            .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_photo_orange))
            .setPositiveButtonIcon(resources.getDrawable(R.drawable.ic_photo_camera_24))
            .setPositiveButton("") { _, _ ->
                intentToCamera()
            }
            .setNegativeButtonIcon(resources.getDrawable(R.drawable.ic_photo_library_24))
            .setNegativeButton("") { _, _ ->
                intentToPictureGallery()
            }
            .setNeutralButtonIcon(resources.getDrawable(R.drawable.ic_cancel_24))
            .setNeutralButton("") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }

    private fun photoDescDialog(photoUri: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle("Short description")

        val input = EditText(this)
        input.hint = "Enter title of picture"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("ok", ){ _, _ ->
            photoDescription = input.text.toString()
        }
        builder.setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        builder.show()


        val currentPhoto = Photo(0, photoDescription, photoUri, 0)
        photosList.add(currentPhoto)
        Log.e("listPhoto", "${photosList.last().shortDescription} + ${photosList.last().photoUri}")

    }

    private fun displayPhotoFunction(displayPhotos: ImageView, uri: Uri) {
        displayPhotos.setImageURI(uri)
    }

    private fun intentToCamera() {
//        val getContent = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
//
//            if (result.resultCode == Activity.RESULT_OK){
//                val uri = result.data
//            }
//
//        }
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            startActivityForResult(it, 9)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (resultCode == Activity.RESULT_OK && requestCode == 0 || requestCode == 9) {
            val uri = intentData?.data
            //TODO Ajouter une méthode pour ajouter un string pour la description de la photo

            photoDescDialog(uri.toString())
            propertyPhotos.add(uri.toString())
            Log.e("photo", uri.toString())
            for (i in 0..propertyPhotos.size) {
                Log.e("for photo", propertyPhotos.toString())
            }

        }
    }

    private fun intentToPictureGallery() {

        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, 0)
        }

    }


    private fun typeOfPropertyChoise(chipId: Int): String {
        var userChoice = ""
        when (chipId) {

            R.id.chipHouse -> userChoice = "House"
            R.id.chipManor -> userChoice = "Manor"
            R.id.chipCastle -> userChoice = "Castle"
            R.id.chipDuplex -> userChoice = "Duplex"
            R.id.chipPenthouse -> userChoice = "Penthouse"
            R.id.chipLoft -> userChoice = "Loft"


        }
        return userChoice
    }

    private fun pointsOfInterestToString(chipId: List<Int>): String{
        var pointsOfInterestProperty = ""

        //TODO a virer mettre une liste à la place
        for (i  in 0..chipId.size)
            when(chipId.indexOf(i)){

                R.id.chipSchool -> pointsOfInterestProperty += "School "
                R.id.chipPark -> pointsOfInterestProperty += "Park "
                R.id.chipShops -> pointsOfInterestProperty += "Shops "
                R.id.chipTransport -> pointsOfInterestProperty += "Transport "

            }

        return pointsOfInterestProperty
    }


}
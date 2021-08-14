package com.openclassrooms.realestatemanager

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.adapter.ViewPagerAdapter
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModelFactory

private const val REQUEST_CODE_IMAGE_PICK = 0
private const val REQUEST_CODE_TAKE_IMAGE_WITH_CAMERA = 9

class AddPropertyActivity : AppCompatActivity() {

    var propertyPhotos = arrayListOf<Photo>()
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
        //TODO ne pas oublier l'intent
        val btnHome: ImageView = findViewById(R.id.ivHome)

        //Views for Pictures
        val addPhoto: ImageButton = findViewById(R.id.ibAddPhoto)

        //Chips for type of property
        val chipGroupTypeOfProperty: ChipGroup = findViewById(R.id.chipGroupTypeOfProperty)

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

        //View for description
        val description: TextInputEditText = findViewById(R.id.tietDescription)

        //View for create Property
        val btnCreate: ImageButton = findViewById(R.id.btnCreate)

        //For ViewPager and TabLayout
        val viewPager: ViewPager2 = findViewById(R.id.vpPropertyPhotos)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        var adapter = ViewPagerAdapter(photosList)
        viewPager.adapter = adapter


        val addPhotoDialog = addPhotoAlertDialog()



        addPhoto.setOnClickListener {
            displayDotsIndicators(tabLayout, viewPager)
            addPhotoDialog.show()
        }

        entryDateClicked(entryDate)



        btnCreate.setOnClickListener {

            val selectedChipId = chipGroupTypeOfProperty.checkedChipId

            val userChooseInString = typeOfPropertyChoise(selectedChipId)

            //TODO modifier l'adresse en latitude logitude
            val completeAddress =
                "${address.text.toString()}, ${zipCode.text.toString()}, ${state.text.toString()}"

            val checkedPointOfInterestIds = groupChipsPointsOfInterest.checkedChipIds


            val newProperty = Property(
                id = 0,
                price = price.text.toString().toInt(),
                type = userChooseInString,
                area = areaValue.text.toString().toInt(),
                roomsNumber = roomsValue.text.toString().toInt(),
                bedRoomsNumber = bedroomsValue.text.toString().toInt(),
                bathRoomsNumber = bathroomsValue.text.toString().toInt(),
                description = description.text.toString(),
                address = completeAddress,
                pointOfInterest = pointsOfInterestToString(checkedPointOfInterestIds),
                entryDate = entryDate.text.toString(),
                saleDate = "",
                estateAgent = "Me"
            )

            pointsOfInterestToString(checkedPointOfInterestIds)


            val pointOfInterest = groupChipsPointsOfInterest.checkedChipIds
            Log.e("here", "${pointsOfInterestToString(pointOfInterest)}")
            propertyViewModel.upsertPropertyAndPhotos(newProperty, propertyPhotos)
                .observe(this, {
                    Log.e("property id", "$it")
                    finish()
                })

        }


    }


    private fun entryDateClicked(entryDate: TextInputEditText) {
        entryDate.setOnClickListener {
            displayCalendar(entryDate)

        }
    }

    private fun displayCalendar(entryDate: TextInputEditText) {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()


        datePicker.show(supportFragmentManager, "Property Entry Date")
        datePicker.addOnPositiveButtonClickListener {
            entryDate.setText(datePicker.headerText)
        }

    }

    private fun displayDotsIndicators(
        tabLayout: TabLayout,
        viewPager: ViewPager2
    ) {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->


        }.attach()
    }

    private fun addPhotoAlertDialog(): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Add Photo")
            .setMessage("Choose one in your gallery or take it with your camera")
            .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_add_photo_orange))
            .setPositiveButtonIcon(ContextCompat.getDrawable(this, R.drawable.ic_photo_camera_24))
            .setPositiveButton("") { _, _ ->
                intentToCamera()
            }
            .setNegativeButtonIcon(ContextCompat.getDrawable(this, R.drawable.ic_photo_library_24))
            .setNegativeButton("") { _, _ ->
                intentToPictureGallery()
            }
            .setNeutralButtonIcon(ContextCompat.getDrawable(this, R.drawable.ic_cancel_24))
            .setNeutralButton("") { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }


    private fun photoDescDialog(photoUri: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle("Image name")

        val input = EditText(this)
        input.hint = "Enter name of picture"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("ok") { _, _ ->
            photoDescription = input.text.toString()
            val currentPhoto = Photo(0, photoDescription, photoUri, 0)

            photosList.add(currentPhoto)
            Log.e(
                "listPhoto",
                "${photosList.last().shortDescription} + ${photosList.last().photoUri}"
            )

        }
        builder.setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        builder.show()


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
            startActivityForResult(it, REQUEST_CODE_TAKE_IMAGE_WITH_CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (resultCode == Activity.RESULT_OK &&
            requestCode == REQUEST_CODE_IMAGE_PICK ||
            requestCode == REQUEST_CODE_TAKE_IMAGE_WITH_CAMERA
        ) {
            val uri = intentData?.data

            photoDescDialog(uri.toString())
        }
    }

    private fun intentToPictureGallery() {

        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
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

    private fun pointsOfInterestToString(chipId: List<Int>): ArrayList<String> {
        val pointsOfInterestProperty = arrayListOf<String>()

        chipId.forEach {
            when (it) {
                R.id.chipSchool -> pointsOfInterestProperty += "School "
                R.id.chipPark -> pointsOfInterestProperty += "Park "
                R.id.chipShops -> pointsOfInterestProperty += "Shops "
                R.id.chipTransport -> pointsOfInterestProperty += "Transport "
            }
        }


        return pointsOfInterestProperty
    }


}
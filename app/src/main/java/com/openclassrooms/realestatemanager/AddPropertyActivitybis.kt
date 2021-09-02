package com.openclassrooms.realestatemanager

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import com.openclassrooms.realestatemanager.adapter.ViewPagerAdapter
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import com.openclassrooms.realestatemanager.repositories.UserRepository
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModelFactory
import com.openclassrooms.realestatemanager.viewmodel.UserViewModel
import com.openclassrooms.realestatemanager.viewmodel.UserViewModelFactory
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.jvm.Throws

private const val REQUEST_CODE_IMAGE_PICK = 0
private const val REQUEST_CODE_TAKE_IMAGE_WITH_CAMERA = 9


class AddPropertyActivity : AppCompatActivity() {
    lateinit var bindings: ActivityAddPropertyBinding


    lateinit var photoUri: Uri
    var photoDescription = ""
    var photosList = arrayListOf<Photo>()
    lateinit var adapter: ViewPagerAdapter
    lateinit var viewPager: ViewPager2
    lateinit var currentPhotoPath: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = bindings.root
        setContentView(view)


        //For Property ViewModels
        val propertyRepository = PropertyRepository(PropertyDatabase.invoke(this))
        val factory = PropertyViewModelFactory(propertyRepository)
        val propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        //For User ViewModels
        val userRepository = UserRepository()
        val userFactory = UserViewModelFactory(userRepository)
        val userViewModel = ViewModelProvider(this, userFactory).get(UserViewModel::class.java)


        //For ViewPager and TabLayout
        viewPager = bindings.vpPropertyPhotos
        adapter = ViewPagerAdapter(photosList)
        viewPager.adapter = adapter
        displayDotsIndicators(bindings.tabLayout, viewPager)


        val addPhotoDialog = addPhotoAlertDialog()



        bindings.ibAddPhoto.setOnClickListener {
            addPhotoDialog.show()
        }

        entryDateClicked(bindings.tietEntryDate)

        createPropertyInDB(propertyViewModel, userViewModel)


    }

    private fun createPropertyInDB(propertyViewModel: PropertyViewModel, userViewModel: UserViewModel) {
        bindings.btnCreate.setOnClickListener {

            var allIsGood = true
            var isGood: Boolean
            listOfInputs().forEach {
                isGood = checkWrongOrEmptyValues(it.second, it.first)
                Log.e("isGood", "$isGood + ${it.first}")
                if (!isGood) {
                    it.first.requestFocus()
                    it.first.error = "Required *"
                    allIsGood = false
                }
            }

            if (allIsGood) {
                val userChooseInString =
                    typeOfPropertyChoice(bindings.chipGroupTypeOfProperty.checkedChipId)

                //TODO modifier l'adresse en latitude logitude
                val completeAddress =
                    "${bindings.tietAddress.text.toString()}, ${bindings.tietZipCode.text.toString()}, ${bindings.tietState.text.toString()}"

                val checkedPointOfInterestIds = bindings.chipsGroupPointsOfInterest.checkedChipIds



                var userName = ""
                userViewModel.currentUser().observe(this, {
                    userName = it?.displayName.toString()
                })
                val newProperty = Property(
                    id = 0,
                    price = bindings.tietPrice.text.toString().toInt(),
                    type = userChooseInString,
                    area = bindings.tietArea.text.toString().toInt(),
                    roomsNumber = bindings.tietRooms.text.toString().toInt(),
                    bedRoomsNumber = bindings.tietBedrooms.text.toString().toInt(),
                    bathRoomsNumber = bindings.tietBathrooms.text.toString().toInt(),
                    description = bindings.tietDescription.text.toString(),
                    address = completeAddress,
                    pointOfInterest = pointsOfInterestToString(checkedPointOfInterestIds),
                    entryDate = bindings.tietEntryDate.text.toString(),
                    saleDate = "",
                    estateAgent = userName
                )

                pointsOfInterestToString(checkedPointOfInterestIds)


                propertyViewModel.upsertPropertyAndPhotos(newProperty, photosList)
                    .observe(this, {
                        Log.e("property id", "$it + ${photosList.size}")
                        finish()
                    })

            } else {
                Toast.makeText(this, "please fill all input", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun checkWrongOrEmptyValues(
        inputEditText: TextInputEditText,
        inputLayout: TextInputLayout
    ): Boolean {
        var isGood = true


        if (inputEditText.text.isNullOrBlank() || inputEditText.text.isNullOrEmpty()) {
            inputLayout.error = "Required *"
            isGood = false

        }

        return isGood
    }

    private fun listOfInputs(): List<Pair<TextInputLayout, TextInputEditText>> {

        return listOf(
            Pair(bindings.tilPrice, bindings.tietPrice),
            Pair(bindings.tilEntryDate, bindings.tietEntryDate),
            Pair(bindings.tilArea, bindings.tietArea),
            Pair(bindings.tilRooms, bindings.tietRooms),
            Pair(bindings.tilBathrooms, bindings.tietBathrooms),
            Pair(bindings.tilBedrooms, bindings.tietBedrooms),
        )
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
            adapter.notifyDataSetChanged()
            viewPager.currentItem = photosList.size - 1

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
        //TODO
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: IOException) {
                    Log.e("intentToCamera", e.toString())
                    null
                }
                photoFile?.also {
                    photoUri = FileProvider.getUriForFile(
                        this,
                        "com.openclassrooms.realestatemanager.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_IMAGE_WITH_CAMERA)
                }
            }

        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        //Create an image file name
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timestamp}",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (resultCode == Activity.RESULT_OK) {
            Log.e("ResultOk photos", "intentData.extra = ${intentData?.extras?.get("intentData")} / intentData.data = ${intentData?.data}")
            val uri: Uri? = when (requestCode) {
                REQUEST_CODE_TAKE_IMAGE_WITH_CAMERA -> photoUri
                REQUEST_CODE_IMAGE_PICK -> intentData?.data
                else -> null
            }
            photoDescDialog(uri.toString())
        }

    }


    private fun intentToPictureGallery() {

        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, REQUEST_CODE_IMAGE_PICK)
        }

    }


    private fun typeOfPropertyChoice(chipId: Int): String {
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
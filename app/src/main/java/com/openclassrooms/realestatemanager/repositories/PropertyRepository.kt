package com.openclassrooms.realestatemanager.repositories

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PropertyRepository(private val db: PropertyDatabase) {

    private var storageImageRef = Firebase.storage.reference
    private val firestoreDb = Firebase.firestore


    fun upsertProperty(item: Property) = liveData {
        val id = db.getPropertyDao().upsert(item)
        emit(id)
    }

    fun upsertPropertyAndPhotos(item: Property, photos: List<Photo>) = liveData {
        val idProperty = db.getPropertyDao().upsert(item)
        val photosUri = ArrayList<String>()
        photos.forEach {
            Log.e("photo?", "${it.shortDescription} + ${it.photoUri}")
            it.propertyId = idProperty
            db.getPropertyDao().insertPhoto(it)
            CoroutineScope(Dispatchers.IO).launch {
                val photoUri = Uri.parse(it.photoUri)
                val gson = Gson()
                storageImageRef.child("image/${it.propertyId}${it.shortDescription}")
                    .putFile(photoUri)
                photosUri.add("${it.propertyId}${it.shortDescription}")
                Log.e("repo Photo", "${it.propertyId}")
                Log.e("repo gson", gson.toJson(it))
            }

        }

        item.id = idProperty
        insertPropertyInFirestore(item, photos)


        emit(idProperty)
    }

    suspend fun delete(item: Property) = db.getPropertyDao().delete(item)

    suspend fun insertPhoto(item: Photo) = db.getPropertyDao().insertPhoto(item)

    fun getAllProperties() = liveData {
        val listOfProperties = db.getPropertyDao().getAllProperties()
        emit(listOfProperties)
        //TODO faire l'appel firestore ici

    }

    fun getAllPhotosOfProperty(item: Property) = liveData {
        val listOfPhotos = db.getPropertyDao().getPropertyWithPhotos(item.id!!)
        emit(listOfPhotos)
    }

    fun getAllProperties2(): MutableLiveData<List<Property>>{
        val properties = MutableLiveData<List<Property>>()
        CoroutineScope(Dispatchers.IO).launch {
            properties.postValue(db.getPropertyDao().getAllProperties().value)
            getAllPropertiesInFirebase(properties)
        }

        return properties
    }

    private fun getAllPropertiesInFirebase(properties: MutableLiveData<List<Property>>) {
        val propertiesList = ArrayList<Property>()
        firestoreDb.collection("properties")
            .get()
            .addOnCompleteListener {
                 it.result!!.documents.forEach { document ->
                     val currentProperty = document.toObject(Property::class.java)
                     propertiesList.add(currentProperty!!)
                     getPhotosOfProperty("${currentProperty.id}")
                     Log.e("fGetProperties", "${propertiesList.size}")
                 }
                properties.postValue(propertiesList)
            }

    }

    private fun getPhotosOfProperty(id: String) {
        val photosList = ArrayList<Photo>()
        firestoreDb.collection("properties")
            .document(id)
            .collection("photos")
            .get()
            .addOnCompleteListener {
                it.result!!.documents.forEach { document ->
                    val currentPhoto = document.toObject(Photo::class.java)
                    photosList.add(currentPhoto!!)
                }

            }
    }


    fun uploadPhotoToFirebaseStorage(item: Photo, fileName: String) = liveData {
        val photoUri = Uri.parse(item.photoUri)
        val uploadImageToFirebaseStorage =
            storageImageRef.child("image/$fileName").putFile(photoUri)
        emit(uploadImageToFirebaseStorage)
        Log.e("repo", item.photoUri)
    }

    fun uploadPhotoWithCoroutines(item: Photo, fileName: String) =
        CoroutineScope(Dispatchers.IO).launch {

            try {
                val photoUri = Uri.parse(item.photoUri)
                storageImageRef.child("image/$fileName").putFile(photoUri)
                Log.e("repo Coroutine", item.photoUri)
            } catch (e: Exception) {
                Log.e("repo Coroutine", e.message!!)
            }


        }

    fun downloadPhotoInFirebaseStorage(propertyId: Long) = CoroutineScope(Dispatchers.IO).launch {


    }

    private fun insertPropertyInFirestore(property: Property, photos: List<Photo>)  {

        //TODO refactor to livedata
        Log.e("upsertPropertyFirestore", "dedans")
        FirebaseFirestore.setLoggingEnabled(true)
        firestoreDb.collection("properties")
            .document("${property.id}+${property.estateAgent}")
            .set(property)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val gson = Gson()
                    Log.e("lambda", "ok + ${photos.size} + ${gson.toJson(photos)} ")
                    addPhotosInFirestore(photos, property)
                }
            }

    }

    private fun addPhotosInFirestore(
        photos: List<Photo>,
        property: Property
    ) {
        //TODO refactor to livedata
        photos.forEach {
            Log.e("forE photocall", "${it.propertyId}${it.photoUri}")
            firestoreDb
                .collection("properties")
                .document("${property.id}+${property.estateAgent}")
                .collection("photos")
                .add(it)

        }
    }







}




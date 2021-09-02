package com.openclassrooms.realestatemanager.repositories

import android.net.Uri
import android.util.Log
import androidx.lifecycle.liveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.database.PropertyWithoutSQL
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
    }

    fun getAllPhotosOfProperty(item: Property) = liveData {
        val listOfPhotos = db.getPropertyDao().getPropertyWithPhotos(item.id!!)
        emit(listOfPhotos)
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

        val firestoreProperty = convertProperty(property)
        convertProperty(property)
        Log.e("upsertPropertyFirestore", "dedans")
        FirebaseFirestore.setLoggingEnabled(true)
        firestoreDb.collection("properties")
            .document("${firestoreProperty.id}")
            .set(firestoreProperty)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val gson = Gson()
                    Log.e("lambda", "ok + ${photos.size} + ${gson.toJson(photos)} ")
                    addPhotosInFirestore(photos, firestoreProperty.id.toString())
                }
            }

    }

    private fun addPhotosInFirestore(
        photos: List<Photo>,
        PropertyId: String
    ) {
        photos.forEach {
            Log.e("forE photocall", "${it.propertyId}${it.photoUri}")
            firestoreDb
                .collection("properties")
                .document(PropertyId)
                .collection("photos")
                .add(it)

        }
    }


    private fun convertProperty(property: Property): PropertyWithoutSQL {

        return PropertyWithoutSQL(
            property.id,
            property.type,
            property.price,
            property.area,
            property.roomsNumber,
            property.bedRoomsNumber,
            property.bathRoomsNumber,
            property.description,
            property.address,
            property.pointOfInterest,
            property.entryDate,
            property.saleDate,
            property.estateAgent
        )
    }


}




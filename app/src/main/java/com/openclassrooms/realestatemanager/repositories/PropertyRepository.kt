package com.openclassrooms.realestatemanager.repositories

import android.net.Uri
import android.util.Log
import androidx.lifecycle.liveData
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PropertyRepository(private val db: PropertyDatabase) {

    var storageImageRef = Firebase.storage.reference


    //TODO utiliser les livedatascope dans l'ensemble du repo
    fun upsertProperty(item: Property) = liveData {
        val id = db.getPropertyDao().upsert(item)
        emit(id)
    }

    fun upsertPropertyAndPhotos(item: Property, photos: List<Photo>) = liveData {
        val idProperty = db.getPropertyDao().upsert(item)
        photos.find { it.propertyId == 0L }?.propertyId = idProperty
        photos.forEach {
            db.getPropertyDao().insertPhoto(it)
        }
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

    //Ne fonctionne pas l'image n'est pas uploader sur storage
    fun uploadPhotoToFirestore(item: Photo, fileName: String) = liveData {
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

    fun downloadPhotoInFirestore(propertyId: Long) = CoroutineScope(Dispatchers.IO).launch {


    }


}




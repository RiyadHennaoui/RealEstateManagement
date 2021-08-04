package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyWithPhotos
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyViewModel(private val repository: PropertyRepository) : ViewModel() {

    //TODO voir avec Thie les Livedata avec coroutines

    fun upsert(item: Property) = repository.upsertProperty(item)



    fun upsertAllPhotosOfProperty(item: Property, listPhotos: List<Photo>) =
        repository.upsertPropertyAndPhotos(item, listPhotos)


    fun insertPhoto(item: Photo) = CoroutineScope(Dispatchers.IO).launch {
        repository.insertPhoto(item)
    }

    fun delete(item: Property) = CoroutineScope(Dispatchers.IO).launch {
        repository.delete(item)
    }

    fun getAllProperties() = repository.getAllProperties()


    fun getAllPhotosOfProperty(item: Property) = repository.getAllPhotosOfProperty(item)

    fun uploadPhotoToFirebase(item: Photo, fileName: String) = repository.uploadPhotoToFirestore(item, fileName)

    fun uploadPhotoWithCoroutine(item: Photo, fileName: String) = repository.uploadPhotoWithCoroutines(item, fileName)
}
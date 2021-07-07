package com.openclassrooms.realestatemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyViewModel(private val repository: PropertyRepository): ViewModel() {

    fun upsert(item: Property) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsertProperty(item)
    }

    fun insertPhoto(item: Photo) = CoroutineScope(Dispatchers.Main).launch {
        repository.insertPhoto(item)
    }

    fun delete(item: Property) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    fun getAllProperties() = repository.getAllProperties()

    fun getAllPhotosOfProperty(item: Property) = CoroutineScope(Dispatchers.Main).launch {
        repository.getAllPhotosOfProperty(item)
    }
}
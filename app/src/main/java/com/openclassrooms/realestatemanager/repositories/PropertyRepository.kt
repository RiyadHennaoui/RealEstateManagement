package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.database.PropertyWithPhotos


class PropertyRepository(private val db: PropertyDatabase) {



    suspend fun upsertProperty(item: Property) = db.getPropertyDao().upsert(item)

    fun upsertPropertyAndPhotos(item: Property, photos: List<Photo>) = liveData{
        val idProperty = db.getPropertyDao().upsert(item)
        photos.find { it.propertyId == 0L }?.propertyId = idProperty
        photos.forEach {
            db.getPropertyDao().insertPhoto(it)
        }
        emit(idProperty)
    }

    suspend fun delete(item: Property) = db.getPropertyDao().delete(item)

    suspend fun insertPhoto(item: Photo) = db.getPropertyDao().insertPhoto(item)

    fun getAllProperties() = db.getPropertyDao().getAllProperties()

    fun getAllPhotosOfProperty(item: Property) = db.getPropertyDao().getPropertyWithPhotos(item.id!!)

}




package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase
import com.openclassrooms.realestatemanager.database.PropertyWithPhotos


class PropertyRepository(private val db: PropertyDatabase) {


    //TODO Livedata Bordel !!
    suspend fun upsertProperty(item: Property) = db.getPropertyDao().upsert(item)

    suspend fun upsertPropertyAndPhotos(item: Property, photos: List<Photo>): LiveData<List<Photo>> {
        var albumMutableLiveData = MutableLiveData<List<Photo>>()
        val idProperty = db.getPropertyDao().upsert(item)
        photos.find { it.propertyId == 0L }?.propertyId = idProperty
        albumMutableLiveData.postValue(photos)
        photos.forEach {
            db.getPropertyDao().insertPhoto(it)
        }

        return albumMutableLiveData
    }

    suspend fun delete(item: Property) = db.getPropertyDao().delete(item)

    suspend fun insertPhoto(item: Photo) = db.getPropertyDao().insertPhoto(item)

    fun getAllProperties(): LiveData<List<Property>> = db.getPropertyDao().getAllProperties()

    suspend fun getAllPhotosOfProperty(item: Property): LiveData<List<PropertyWithPhotos>> =
        db.getPropertyDao().getPropertyWithPhotos(item.id!!)

}




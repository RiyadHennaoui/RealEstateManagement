package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.database.Photo
import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase

class PropertyRepository(private val db: PropertyDatabase) {

    suspend fun upsertProperty(item: Property) = db.getPropertyDao().upsert(item)

    suspend fun delete(item: Property) = db.getPropertyDao().delete(item)

    suspend fun insertPhoto(item: Photo) = db.getPropertyDao().insertPhoto(item)

    fun getAllProperties() = db.getPropertyDao().getAllProperties()

    suspend fun getAllPhotosOfProperty(item: Property) =
        db.getPropertyDao().getPropertyWithPhotos(item.id!!)

}
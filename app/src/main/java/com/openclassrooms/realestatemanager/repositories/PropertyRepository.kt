package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.database.Property
import com.openclassrooms.realestatemanager.database.PropertyDatabase

class PropertyRepository(private val db: PropertyDatabase) {

    suspend fun upsert(item: Property) = db.getPropertyDao().upsert(item)

    suspend fun delete(item: Property) = db.getPropertyDao().delete(item)

    fun getAllProperties() = db.getPropertyDao().getAllProperties()

}
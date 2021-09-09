package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface PropertyDao {

    //for update and insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Property): Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: Photo)

    @Delete
    suspend fun delete(item: Property)

    @Transaction
    @Query("SELECT * from property")
    fun getAllProperties(): LiveData<List<Property>>

    //TODO a tester
    @Transaction
    @Query("SELECT * from property, photo WHERE property.id = :propertyId AND property.id = propertyId")
    fun getPropertyWithPhotos(propertyId: Long): LiveData<PropertyWithPhotos>

    //TODO a tester
    @Transaction
    @Query("SELECT * from property, photo WHERE property.id = propertyId")
    fun getAllPropertiesWithPhotos(): LiveData<List<PropertyWithPhotos>>
}
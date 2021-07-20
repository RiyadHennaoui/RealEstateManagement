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

    @Transaction
    @Query("SELECT * from property WHERE id = :propertyId")
    suspend fun getPropertyWithPhotos(propertyId: Long): List<PropertyWithPhotos>
}
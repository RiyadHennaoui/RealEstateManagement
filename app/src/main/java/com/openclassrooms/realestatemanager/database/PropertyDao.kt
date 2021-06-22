package com.openclassrooms.realestatemanager.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface PropertyDao {

    //for update and insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Property)


    @Delete
    suspend fun delete(item: Property)

    @Query("SELECT * from property")
    fun getAllProperties(): LiveData<List<Property>>
}
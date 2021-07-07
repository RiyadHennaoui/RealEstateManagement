package com.openclassrooms.realestatemanager.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo")
data class Photo (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val shortDescription: String,
    val photoUri: String,
    val propertyId: String
)
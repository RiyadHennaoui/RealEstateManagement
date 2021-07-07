package com.openclassrooms.realestatemanager.database

import androidx.room.Embedded
import androidx.room.Relation

data class PropertyWithPhotos (
    @Embedded val property: Property,
    @Relation(
        parentColumn = "id",
        entityColumn = "propertyId"
    )
    val photos: List<Photo>

)
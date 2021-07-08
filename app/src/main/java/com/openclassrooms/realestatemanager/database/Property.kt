package com.openclassrooms.realestatemanager.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity (tableName = "property")
data class Property (@PrimaryKey (autoGenerate = true) var id: Int? = null,
                         @ColumnInfo (name = "type") var type: String,
                         @ColumnInfo (name = "price") var price: Int,
                         @ColumnInfo (name = "area") var area: Int,
                         @ColumnInfo (name = "roomsNumber") var roomsNumber: Int,
                         @ColumnInfo (name = "bedRoomsNumber") var bedRoomsNumber: Int,
                         @ColumnInfo (name = "bathRoomsNumber") var bathRoomsNumber: Int,
                         @ColumnInfo (name = "description") var description: String,
                         @ColumnInfo (name = "picture") var photoUrl: String,
                         @ColumnInfo (name = "address") var address: String,
                         @ColumnInfo (name = "pointOfInterest") var pointOfInterest: String,
                         @ColumnInfo (name = "entryDate") var entryDate: String,
                         @ColumnInfo (name = "saleDate") var saleDate: String,
                         @ColumnInfo (name = "estateAgent") var estateAgent: String)

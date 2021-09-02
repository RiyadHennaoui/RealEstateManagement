package com.openclassrooms.realestatemanager.database

data class PropertyWithoutSQL (var id: Long,
                               var type: String,
                               var price: Int,
                               var area: Int,
                               var roomsNumber: Int,
                               var bedRoomsNumber: Int,
                               var bathRoomsNumber: Int,
                               var description: String,
                               var address: String,
                               var pointOfInterest: ArrayList<String>,
                               var entryDate: String,
                               var saleDate: String,
                               var estateAgent: String,
)

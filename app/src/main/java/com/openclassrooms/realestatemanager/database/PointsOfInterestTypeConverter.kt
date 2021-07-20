package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PointsOfInterestTypeConverter {
    @TypeConverter
    fun fromString(value: String?): ArrayList<String>{

        val listType = object: TypeToken<ArrayList<String>>(){}.type

        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArraylist(list: ArrayList<String?>): String{

        return Gson().toJson(list)

    }
}
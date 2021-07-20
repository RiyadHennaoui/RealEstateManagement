package com.openclassrooms.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        Property::class,
        Photo::class
               ],
    version = 1,
)
@TypeConverters(PointsOfInterestTypeConverter::class)
abstract class PropertyDatabase: RoomDatabase() {

    abstract fun getPropertyDao(): PropertyDao

    companion object {
        //for make sure that only one thread can write in database
        @Volatile
        private var instance: PropertyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it }
        }
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                PropertyDatabase::class.java, "PropertyDB.db").build()
    }
}
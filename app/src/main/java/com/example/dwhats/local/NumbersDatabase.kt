package com.example.dwhats.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dwhats.data.NumberW

@Database(entities = [NumberW::class], version = 1, exportSchema = false)
abstract class NumbersDatabase : RoomDatabase() {

    abstract fun numbersDao(): NumbersDAO

    companion object {

        @Volatile
        private var INSTANCE: NumbersDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NumbersDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NumbersDatabase::class.java,
                    "numbers_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE!!

        }
    }


}
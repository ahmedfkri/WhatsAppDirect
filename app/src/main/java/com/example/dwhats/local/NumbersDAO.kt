package com.example.dwhats.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dwhats.data.NumberW

@Dao
interface NumbersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumber(number: NumberW)

    @Query("SELECT * FROM numbers")
    fun getAllNumbers(): LiveData<List<NumberW>>

    @Delete
    suspend fun deleteNumber(number: NumberW)



}
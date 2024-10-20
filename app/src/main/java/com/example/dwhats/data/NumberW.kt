package com.example.dwhats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers")
data class NumberW(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val num: String
)
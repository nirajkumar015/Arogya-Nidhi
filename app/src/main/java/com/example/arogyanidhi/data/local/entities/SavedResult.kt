package com.example.arogyanidhi.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_results")
data class SavedResult(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val schemeName: String
)

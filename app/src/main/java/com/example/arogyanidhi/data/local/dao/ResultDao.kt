package com.example.arogyanidhi.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.arogyanidhi.data.local.entities.SavedResult

@Dao
interface ResultDao {
    @Insert
    suspend fun insert(result: SavedResult)

    @Query("SELECT * FROM saved_results")
    fun getAllResults(): LiveData<List<SavedResult>>
}

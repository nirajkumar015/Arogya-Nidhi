package com.example.arogyanidhi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.arogyanidhi.data.local.database.AppDatabase
import com.example.arogyanidhi.data.local.entities.SavedResult
import kotlinx.coroutines.launch

class SchemeViewModel(application: Application) : AndroidViewModel(application) {
    private val resultDao = AppDatabase.getDatabase(application).resultDao()
    val allResults: LiveData<List<SavedResult>> = resultDao.getAllResults()

    fun saveResult(result: SavedResult) {
        viewModelScope.launch {
            resultDao.insert(result)
        }
    }
}

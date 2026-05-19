package com.example.arogyanidhi.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.arogyanidhi.data.local.dao.ResultDao
import com.example.arogyanidhi.data.local.entities.SavedResult

@Database(entities = [SavedResult::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun resultDao(): ResultDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "arogya_nidhi_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

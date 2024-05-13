package com.example.focusflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.focusflow.database.daos.FocusFlowDao
import com.example.focusflow.database.entities.FocusFlow

@Database(entities = [FocusFlow::class],version=1)
abstract class FocusFlowDatabase : RoomDatabase() {
    abstract fun dao(): FocusFlowDao

    companion object {
        @Volatile
        private var instance: FocusFlowDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FocusFlowDatabase::class.java,
                "task_db"
            ).build()
    }
}
package com.example.focusflow.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.focusflow.database.daos.FocusFlowDao
import com.example.focusflow.database.entities.FocusFlow

@Database(entities = [FocusFlow::class],version=1)
abstract class FocusFlowDatabase : RoomDatabase() {
    abstract fun dao(): FocusFlowDao
}
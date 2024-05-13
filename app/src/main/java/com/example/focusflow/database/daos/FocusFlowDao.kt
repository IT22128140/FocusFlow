package com.example.focusflow.database.daos

import androidx.room.*
import com.example.focusflow.CardInfo
import com.example.focusflow.database.entities.FocusFlow

@Dao
interface FocusFlowDao {
    @Insert
    suspend fun insertTask(focusFlow: FocusFlow)

    @Update
    suspend fun updateTask(focusFlow: FocusFlow)

    @Update
    suspend fun updateStatus(focusFlow: FocusFlow)

    @Delete
    suspend fun deleteTask(focusFlow: FocusFlow)

    @Query("Delete from FocusFlow")
    suspend fun deleteAll()

    @Query("Select * from FocusFlow")
    suspend fun getTasks():List<CardInfo>

}
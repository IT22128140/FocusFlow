package com.example.focusflow.database.entities

import android.webkit.WebSettings.RenderPriority
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FocusFlow")
data class FocusFlow(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var desc:String,
    var priority:String
)
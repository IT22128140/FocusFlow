package com.example.todolisttutorial

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.focusflow.R
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class TaskItem(
    var name: String,
    var desc: String,
    var dueTime: LocalTime?,
    var completedDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
)
{
    fun isCompleted() = completedDate != null
    fun imageResource(): Int = if(isCompleted()) R.drawable.checked else R.drawable.unchecked
    fun imageColor(context: Context): Int = if(isCompleted()) primary(context) else accent(context)

    private fun primary(context: Context) = ContextCompat.getColor(context, R.color.Primary)
    private fun accent(context: Context) = ContextCompat.getColor(context, R.color.Accent)
}
package com.example.focusflow

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.focusflow.database.FocusFlowDatabase
import com.example.focusflow.database.entities.FocusFlow
import com.example.focusflow.databinding.ActivityCreateCardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalTime

class CreateCard : AppCompatActivity() {
    private lateinit var database: FocusFlowDatabase
    private lateinit var binding: ActivityCreateCardBinding
    private var dueTime: LocalTime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext, FocusFlowDatabase::class.java, "FocusFlow"
        ).build()

        binding.saveButton.setOnClickListener {
            if (binding.createTitle.text.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.createDescription.text.toString().trim { it <= ' ' }.isNotEmpty()
                && binding.priority.selectedItem.toString().isNotEmpty()
                && binding.selectTime.text.toString().isNotEmpty()
            ) {
                val title = binding.createTitle.text.toString()
                val desc = binding.createDescription.text.toString()
                val priority = binding.priority.selectedItem.toString()
                val dueTime = binding.selectTime.text.toString()

                FocusFlowDataObject.setData(title, desc, priority,dueTime,false)
                CoroutineScope(Dispatchers.IO).launch {
                    database.dao().insertTask(FocusFlow(0, title, desc, priority, dueTime, false))
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        binding.cancelButton.setOnClickListener {
            finish()
        }
        binding.selectTime.setOnClickListener {
            openTimePicker()
        }
}
    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(this@CreateCard, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }
    private fun updateTimeButtonText() {
        binding.selectTime.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }
}

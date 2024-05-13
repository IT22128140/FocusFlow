package com.example.focusflow

import android.app.TimePickerDialog
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.focusflow.database.FocusFlowDatabase
import com.example.focusflow.database.entities.FocusFlow
import com.example.focusflow.databinding.ActivityUpdateCardBinding
import kotlinx.coroutines.*
import java.time.LocalTime

class UpdateCard : AppCompatActivity() {
    private lateinit var database: FocusFlowDatabase
    private lateinit var binding: ActivityUpdateCardBinding
    private var dueTime: LocalTime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext, FocusFlowDatabase::class.java, "FocusFlow"
        ).build()

        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {

            val title = FocusFlowDataObject.getData(pos).title
            val desc = FocusFlowDataObject.getData(pos).desc
            val prioritiesArray = resources.getStringArray(R.array.priority)
            val priorities = FocusFlowDataObject.getData(pos).priority
            val priority = prioritiesArray.indexOf(priorities)
            val dueTime = FocusFlowDataObject.getData(pos).dueTime


            binding.createTitle.setText(title)
            binding.createDescription.setText(desc)
            binding.priority.setSelection(priority)
            binding.selectTime.setText(dueTime)

            binding.deleteButton.setOnClickListener {
                FocusFlowDataObject.deleteData(pos)
                CoroutineScope(Dispatchers.IO).launch {
                    database.dao().deleteTaskById(pos+1
                    )
                }
                Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show()
                myIntent()
            }

            binding.updateButton.setOnClickListener {
                FocusFlowDataObject.updateData(
                    pos,
                    binding.createTitle.text.toString(),
                    binding.createDescription.text.toString(),
                    binding.priority.selectedItem.toString(),
                    binding.selectTime.text.toString()

                )
                CoroutineScope(Dispatchers.IO).launch {
                    database.dao().updateTask(
                        FocusFlow(
                            pos + 1,
                            binding.createTitle.text.toString(),
                            binding.createDescription.text.toString(),
                            binding.priority.selectedItem.toString(),
                            binding.selectTime.text.toString(),
                            false
                        )
                    )
                }
                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show()
                myIntent()
            }
            binding.cancelButton.setOnClickListener {
                finish()
            }
            binding.selectTime.setOnClickListener {
                openTimePicker()
            }
        }
    }

    fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(this@UpdateCard, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }
    private fun updateTimeButtonText() {
        binding.selectTime.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }
}

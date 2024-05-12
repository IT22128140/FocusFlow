package com.example.focusflow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.focusflow.database.FocusFlowDatabase
import com.example.focusflow.database.entities.FocusFlow
import com.example.focusflow.databinding.ActivityUpdateCardBinding
import kotlinx.coroutines.*

class UpdateCard : AppCompatActivity() {
    private lateinit var database: FocusFlowDatabase
    private lateinit var binding: ActivityUpdateCardBinding

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

            binding.createTitle.setText(title)
            binding.createDescription.setText(desc)
            binding.priority.setSelection(priority)

            binding.deleteButton.setOnClickListener {
                FocusFlowDataObject.deleteData(pos)
                CoroutineScope(Dispatchers.IO).launch {
                    database.dao().deleteTask(
                        FocusFlow(
                            pos + 1,
                            binding.createTitle.text.toString(),
                            binding.createDescription.text.toString(),
                            binding.priority.selectedItem.toString().trim()
                        )
                    )
                }
                myIntent()
            }

            binding.updateButton.setOnClickListener {
                FocusFlowDataObject.updateData(
                    pos,
                    binding.createTitle.text.toString(),
                    binding.createDescription.text.toString(),
                    binding.priority.selectedItem.toString()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    database.dao().updateTask(
                        FocusFlow(
                            pos + 1,
                            binding.createTitle.text.toString(),
                            binding.createDescription.text.toString(),
                            binding.priority.selectedItem.toString()
                        )
                    )
                }
                myIntent()
            }
        }
    }

    fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

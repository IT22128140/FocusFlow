package com.example.focusflow

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

class CreateCard : AppCompatActivity() {
    private lateinit var database: FocusFlowDatabase
    private lateinit var binding: ActivityCreateCardBinding

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
            ) {
                val title = binding.createTitle.text.toString()
                val desc = binding.createDescription.text.toString()
                val priority = binding.priority.selectedItem.toString()

                FocusFlowDataObject.setData(title, desc, priority)
                CoroutineScope(Dispatchers.IO).launch {
                    database.dao().insertTask(FocusFlow(0, title, desc, priority))
                }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

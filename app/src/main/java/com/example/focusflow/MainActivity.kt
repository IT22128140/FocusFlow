package com.example.focusflow

import FocusFlowAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.focusflow.database.FocusFlowDatabase
import com.example.focusflow.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var database: FocusFlowDatabase
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Room.databaseBuilder(
            applicationContext, FocusFlowDatabase::class.java, "FocusFlow"
        ).build()

        binding.add.setOnClickListener {
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (binding.recyclerView.adapter as? FocusFlowAdapter)?.filter?.filter(newText)
                return false
            }
        })

        binding.deleteAll.setOnClickListener {
            FocusFlowDataObject.deleteAll()
                CoroutineScope(Dispatchers.Main).launch {
                    database.dao().deleteAll()
                    setRecycler()
            }
        }

        setRecycler()
    }

    fun setRecycler() {
        binding.recyclerView.adapter = FocusFlowAdapter(FocusFlowDataObject.getAllData())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}

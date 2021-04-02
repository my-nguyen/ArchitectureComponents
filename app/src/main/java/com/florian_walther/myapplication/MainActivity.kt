package com.florian_walther.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.florian_walther.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModelFactory: NoteViewModelFactory
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteAdapter = NoteAdapter()
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        noteViewModelFactory = NoteViewModelFactory(application)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.getAll().observe(this, {
            // update RecyclerView
            noteAdapter.setNotes(it)
        })
    }
}
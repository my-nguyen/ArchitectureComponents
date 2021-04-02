package com.florian_walther.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.florian_walther.myapplication.AddNoteActivity.Companion.EXTRA_DESCRIPTION
import com.florian_walther.myapplication.AddNoteActivity.Companion.EXTRA_PRIORITY
import com.florian_walther.myapplication.AddNoteActivity.Companion.EXTRA_TITLE
import com.florian_walther.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        const val RC_ADD_NOTE = 1
    }

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
        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, RC_ADD_NOTE)
        }

        noteViewModelFactory = NoteViewModelFactory(application)
        noteViewModel = ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel::class.java)
        noteViewModel.getAll().observe(this, {
            // update RecyclerView
            noteAdapter.notes = it
            noteAdapter.notifyDataSetChanged()
        })

        // swipe to delete
        // swipe left or right only
        val swipeDirs = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, swipeDirs) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                // do nothing here
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteAdapter.notes[position]
                noteViewModel.delete(note)

                Toast.makeText(this@MainActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.rvNotes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_ADD_NOTE && resultCode == RESULT_OK) {
            data?.let {
                val title = it.getStringExtra(EXTRA_TITLE)
                val description = it.getStringExtra(EXTRA_DESCRIPTION)
                val priority = it.getIntExtra(EXTRA_PRIORITY, 1)

                val note = Note(title!!, description!!, priority)
                noteViewModel.insert(note)

                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Note note saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_delete_all_notes -> {
                noteViewModel.deleteAll()
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
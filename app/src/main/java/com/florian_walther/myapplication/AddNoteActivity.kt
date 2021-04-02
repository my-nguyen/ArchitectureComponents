package com.florian_walther.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.florian_walther.myapplication.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TITLE = "com.florian_walther.myapplication.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION = "com.florian_walther.myapplication.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY = "com.florian_walther.myapplication.EXTRA_PRIORITY"
    }

    lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"

        binding.apply {
            npPriority.minValue = 1
            npPriority.maxValue = 10
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {
        binding.apply {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val priority = npPriority.value

            if (title.trim().isEmpty() || description.trim().isEmpty()) {
                Toast.makeText(this@AddNoteActivity, "Please insert a title and description", Toast.LENGTH_SHORT).show()
            } else {
                val data = Intent()
                data.putExtra(EXTRA_TITLE, title)
                data.putExtra(EXTRA_DESCRIPTION, description)
                data.putExtra(EXTRA_PRIORITY, priority)
                setResult(RESULT_OK, data)
                finish()
            }
        }
    }
}
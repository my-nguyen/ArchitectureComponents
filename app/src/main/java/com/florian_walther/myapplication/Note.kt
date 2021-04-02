package com.florian_walther.myapplication

import android.content.Intent
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(val title: String, val description: String, val priority: Int,
                @PrimaryKey(autoGenerate = true) var id: Int = 0) {
    constructor(intent: Intent):
            this(intent.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)!!,
                intent.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)!!,
                intent.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1))
}
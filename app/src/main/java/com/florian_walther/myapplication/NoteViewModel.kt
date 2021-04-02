package com.florian_walther.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val repository = NoteRepository(application)
    private val notes: LiveData<List<Note>> = repository.getAll()

    fun insert(note: Note) = repository.insert(note)

    fun update(note: Note) = repository.update(note)

    fun delete(note: Note) = repository.delete(note)

    fun deleteAll() = repository.deleteAll()

    fun getAll() = notes
}
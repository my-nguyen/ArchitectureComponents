package com.florian_walther.myapplication

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class NoteRepository(application: Application) {
    private val noteDao: NoteDao
    private val notes: LiveData<List<Note>>

    init {
        val database = NoteDatabase.getInstance(application)
        noteDao = database.noteDao()
        notes = noteDao.getAll()
    }

    fun insert(note: Note) {
        InsertAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        UpdateAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteAsyncTask(noteDao).execute(note)
    }

    fun deleteAll() {
        DeleteAllAsyncTask(noteDao).execute()
    }

    fun getAll(): LiveData<List<Note>> = notes

    class InsertAsyncTask(private val noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        override fun doInBackground(vararg notes: Note) {
            noteDao.insert(notes[0])
        }
    }

    class UpdateAsyncTask(private val noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        override fun doInBackground(vararg notes: Note) {
            noteDao.update(notes[0])
        }
    }

    class DeleteAsyncTask(private val noteDao: NoteDao): AsyncTask<Note, Unit, Unit>() {
        override fun doInBackground(vararg notes: Note) {
            noteDao.delete(notes[0])
        }
    }

    class DeleteAllAsyncTask(private val noteDao: NoteDao): AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg notes: Unit) {
            noteDao.deleteAll()
        }
    }
}
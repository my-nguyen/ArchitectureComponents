package com.florian_walther.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM notes")
    fun deleteAll()

    @Query("SELECT * FROM notes ORDER BY priority DESC")
    fun getAll(): LiveData<List<Note>>
}
package com.florian_walther.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    companion object {
        private lateinit var instance: NoteDatabase

        @Synchronized fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "note_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }

    abstract fun noteDao(): NoteDao
}
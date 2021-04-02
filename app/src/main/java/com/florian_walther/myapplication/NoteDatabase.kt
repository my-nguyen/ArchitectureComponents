package com.florian_walther.myapplication

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
    companion object {
        private var instance: NoteDatabase?=null
        private val callback: RoomDatabase.Callback

        init {
            callback = object: RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    PopulateAsyncTask(instance!!).execute()
                }
            }
        }
        @Synchronized fun getInstance(context: Context): NoteDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()
            }
            return instance!!
        }
    }

    abstract fun noteDao(): NoteDao

    class PopulateAsyncTask(noteDatabase: NoteDatabase): AsyncTask<Unit, Unit, Unit>() {

        private val noteDao = noteDatabase.noteDao()

        override fun doInBackground(vararg p0: Unit?) {
            noteDao.insert(Note("Title 1", "Description 1", 1))
            noteDao.insert(Note("Title 2", "Description 2", 2))
            noteDao.insert(Note("Title 3", "Description 3", 3))
        }
    }
}
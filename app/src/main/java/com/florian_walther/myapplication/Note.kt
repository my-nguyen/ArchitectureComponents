package com.florian_walther.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(val title: String, val description: String, val priority: Int,
                @PrimaryKey(autoGenerate=true) val id: Int=0)
package com.example.testnotesapp.data.db.structures

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(
    val title:String,
    val description:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val color: String="111111"
)

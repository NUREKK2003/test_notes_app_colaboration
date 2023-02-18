package com.nomteam.csvnotesapp.data.db.structures

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class NoteEntity(
    val title:String,
    val description:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val color: Int=32423
)

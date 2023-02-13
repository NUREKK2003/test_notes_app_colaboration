package com.example.testnotesapp.data.db.structures

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey val uInt: UInt,
    @ColumnInfo(name = "note_title") val title:String?,
    @ColumnInfo(name = "note_description") val description:String?,
    @ColumnInfo(name = "note_color") val color:String
)

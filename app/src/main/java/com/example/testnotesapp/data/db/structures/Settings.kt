package com.example.testnotesapp.data.db.structures

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class Settings(
    var darkTheme:Boolean,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)

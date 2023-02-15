package com.example.testnotesapp.objects

import androidx.compose.ui.graphics.Color
import com.example.testnotesapp.data.structures.Note

object Constants {
    val DEFAULT_ID:Int=-1

    val DEFAULT_NOTE = Note("","", Color.Yellow, DEFAULT_ID)

    val DEFAULT_NOTE_COLOR = Color.Yellow
}
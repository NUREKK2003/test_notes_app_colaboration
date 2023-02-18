package com.nomteam.csvnotesapp.objects

import androidx.compose.ui.graphics.Color
import com.nomteam.csvnotesapp.data.db.structures.NoteEntity
import com.nomteam.csvnotesapp.data.structures.Note

object Constants {
    val DEFAULT_ID:Int=-1

    val DEFAULT_NOTE = Note("","", Color.Yellow, DEFAULT_ID)

    val DEFAULT_NOTE_ENTITY = NoteEntity("","", -11, DEFAULT_ID)

    val DEFAULT_NOTE_COLOR = Color.Yellow

    val DEFAULT_SETTINGS_ID=1

    val SAVE_COOLDOWN_TIME=2000L
}
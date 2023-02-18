package com.nomteam.csvnotesapp.state

import com.nomteam.csvnotesapp.data.structures.Note

data class NoteUiState(
    val notesList:List<Note> = emptyList(),
    val loading: Boolean = true
)

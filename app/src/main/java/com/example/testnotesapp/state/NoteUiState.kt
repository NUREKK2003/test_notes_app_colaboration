package com.example.testnotesapp.state

import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.data.example.ExampleData
import com.example.testnotesapp.data.structures.Note

data class NoteUiState(
    val notesList:List<Note> = emptyList(),
    val loading: Boolean = true
)

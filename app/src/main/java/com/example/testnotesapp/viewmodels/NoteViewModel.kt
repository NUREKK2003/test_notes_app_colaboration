package com.example.testnotesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.testnotesapp.data.db.NoteDatabase
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.data.repository.NoteRepository
import com.example.testnotesapp.data.structures.Note
import com.example.testnotesapp.state.NoteUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(NoteUiState(loading = true))

    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()



    val readAllData:LiveData<List<NoteEntity>>
    private val repository:NoteRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).NoteDao()
        repository = NoteRepository(noteDao)
        readAllData = repository.readAllData

        viewModelScope.launch {
            var listOfNotes = mutableListOf<Note>()
            readAllData.value?.forEach { noteEntity ->
                listOfNotes.add(Note(noteEntity.title, noteEntity.description, Color.Blue))
            }
            _uiState.update {
                it.copy(notesList = listOfNotes, false)
            }
            //Log.d("TEST123",readAllData.value.toString())
        }
    }

    fun addNote(note: NoteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
        }
    }

    fun updateNote(note: NoteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
        }
    }
    fun deleteNote(note: NoteEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }
    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }



}
package com.example.testnotesapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import com.example.testnotesapp.data.db.NoteDatabase
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.data.repository.NoteRepository
import com.example.testnotesapp.data.structures.Note
import com.example.testnotesapp.state.NoteUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(app: Application):AndroidViewModel(app) {
    private val repository = NoteRepository(app.applicationContext)

    private val _uiState = MutableStateFlow(NoteUiState(loading = true))

    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()


    init {

        var notesEntities = mutableListOf<NoteEntity>()



        viewModelScope.launch {
            //getAllNotes().onEach{ value-> notesEntities = value as MutableList<NoteEntity>  }

            getAllNotes().collect{
                it.forEach { item->
                    notesEntities.add(item)
                    Log.d("TEST123",item.toString())
                }
            }
            Log.d("TEST123",notesEntities.toString())
            var listOfNotes = mutableListOf<Note>()
            notesEntities.forEach { noteEntity ->
                listOfNotes.add(Note(noteEntity.title, noteEntity.description, Color.Blue))
            }
            _uiState.update {
                it.copy(notesList = listOfNotes, false)
            }
        }
    }
    fun getAllNotes():Flow<List<NoteEntity>>{
        return repository.getAllNotes()
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
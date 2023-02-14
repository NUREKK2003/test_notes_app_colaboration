package com.example.testnotesapp.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import com.example.testnotesapp.data.db.NoteDatabase
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.data.repository.NoteRepository
import com.example.testnotesapp.data.structures.Note
import com.example.testnotesapp.objects.Constants
import com.example.testnotesapp.state.NoteUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(app: Application):AndroidViewModel(app) {
    private val repository = NoteRepository(app.applicationContext)

    private val _uiState = MutableStateFlow(NoteUiState(loading = true))

    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    var selectedNote:Note = Note("test","test",Color.White,Constants.DEFAULT_ID)

    // lista notatek
    var listOfNotes = mutableListOf<Note>()

    val notesHandler = getAllNotes()






    init {
        // wczytanie listy notatek

            listInitialization()

    }

    @SuppressLint("SuspiciousIndentation")
    fun listInitialization(){

        viewModelScope.launch {
        var notesEntities = mutableListOf<NoteEntity>()
            notesHandler.collect{
                _uiState.update {
                    it.copy(loading = true)
                }
                notesEntities.clear()
                it.forEach { item->
                    notesEntities.add(item)
                    Log.d("TEST1234",item.toString())
                }
                listOfNotes.clear()
                notesEntities.forEach { noteEntity ->
                    listOfNotes.add(Note(noteEntity.title, noteEntity.description, Color.Blue,noteEntity.id))
                }
                _uiState.update {
                    it.copy(notesList = listOfNotes, false)
                }

            }

        }
    }
    fun getAllNotes():Flow<List<NoteEntity>>{

        return repository.getAllNotes()
    }

    fun getNoteById(id:Int):Note{
        listOfNotes.forEach{note->
            if(note.id==id){
                selectedNote = note
                return selectedNote
            }
        }
        return selectedNote
    }
    fun chooseNoteById(id:Int){
        listOfNotes.forEach{note->
            if(note.id==id){
                selectedNote = note
                return
            }
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
        Log.d("TEST1234",listOfNotes.toString())
        _uiState.value = NoteUiState(loading = true)
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
            Log.d("TEST1234",listOfNotes.toString())

            listInitialization()
            Log.d("TEST1234",listOfNotes.toString())

        }
    }
    fun deleteAllNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotes()
        }
    }



}
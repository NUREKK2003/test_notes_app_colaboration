package com.example.testnotesapp.data.repository

import androidx.lifecycle.LiveData
import com.example.testnotesapp.data.db.dao.NoteDao
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.data.structures.Note

class NoteRepository(private val noteDao: NoteDao) {
    val readAllData: LiveData<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun addNote(note: NoteEntity){
        noteDao.addNote(note)
    }
    suspend fun updateNote(note: NoteEntity){
        noteDao.updateNote(note)
    }
    suspend fun deleteNote(note: NoteEntity){
        noteDao.deleteNote(note)
    }
    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }
}
package com.nomteam.csvnotesapp.data.repository

import android.content.Context
import com.nomteam.csvnotesapp.data.db.NotesDb
import com.nomteam.csvnotesapp.data.db.dao.NoteDao
import com.nomteam.csvnotesapp.data.db.structures.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepository(context: Context): NoteDao {
    private val dao = NotesDb.getDatabase(context).NoteDao()
    override fun getAllNotes(): Flow<List<NoteEntity>>{
        return dao.getAllNotes()
    }


    override suspend fun addNote(note: NoteEntity)  = withContext(Dispatchers.IO){
        dao.addNote(note)
    }
    override suspend fun updateNote(note: NoteEntity)  = withContext(Dispatchers.IO){
        dao.updateNote(note)
    }
    override suspend fun deleteNote(note: NoteEntity)  = withContext(Dispatchers.IO){
        dao.deleteNote(note)
    }
    override suspend fun deleteAllNotes()  = withContext(Dispatchers.IO){
        dao.deleteAllNotes()
    }
}
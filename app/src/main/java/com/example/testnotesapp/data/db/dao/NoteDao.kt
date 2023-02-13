package com.example.testnotesapp.data.db.dao

import androidx.room.*
import com.example.testnotesapp.data.db.structures.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT *")
    fun getAll(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note:NoteEntity)

    @Update
    suspend fun updateNote(note:NoteEntity)

    @Delete
    suspend fun deleteNote(note:NoteEntity)

    @Query("DELETE FROM")
    suspend fun deleteAllNotes()

}
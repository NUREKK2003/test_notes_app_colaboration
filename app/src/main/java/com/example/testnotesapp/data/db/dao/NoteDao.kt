package com.example.testnotesapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testnotesapp.data.db.structures.NoteEntity


@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): LiveData<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNote(note:NoteEntity)

    @Update
    suspend fun updateNote(note:NoteEntity)

    @Delete
    suspend fun deleteNote(note:NoteEntity)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

}
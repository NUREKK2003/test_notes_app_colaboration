package com.example.testnotesapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testnotesapp.data.db.structures.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    // suspend - asynchroniczne
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note:NoteEntity)

    @Update
    suspend fun updateNote(note:NoteEntity)

    @Delete
    suspend fun deleteNote(note:NoteEntity)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

}
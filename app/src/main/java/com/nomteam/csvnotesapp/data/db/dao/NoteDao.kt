package com.nomteam.csvnotesapp.data.db.dao

import androidx.room.*
import com.nomteam.csvnotesapp.data.db.structures.NoteEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    // suspend - asynchroniczne
    @Query("SELECT * FROM note_table")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE) // w przypadku przesłania notatki o tym samym id nastapi aktualizacja
    suspend fun addNote(note:NoteEntity)

    @Update
    suspend fun updateNote(note:NoteEntity)

    @Delete
    suspend fun deleteNote(note:NoteEntity)

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

}
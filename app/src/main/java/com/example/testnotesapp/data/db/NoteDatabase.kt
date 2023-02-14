package com.example.testnotesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testnotesapp.data.db.dao.NoteDao
import com.example.testnotesapp.data.db.structures.NoteEntity


@Database(entities = [NoteEntity::class], version = 2, exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun NoteDao():NoteDao


}
object NotesDb{
    @Volatile
    private var db:NoteDatabase?=null
    fun getDatabase(context: Context): NoteDatabase {
        if(db==null){
            db = Room.databaseBuilder(
                context,
                NoteDatabase::class.java,
                "note_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return db!!
    }
}
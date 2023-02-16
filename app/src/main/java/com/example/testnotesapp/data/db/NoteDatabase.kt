package com.example.testnotesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testnotesapp.data.db.dao.NoteDao
import com.example.testnotesapp.data.db.dao.SettingsDao
import com.example.testnotesapp.data.db.structures.NoteEntity
import com.example.testnotesapp.data.db.structures.Settings


@Database(entities = [NoteEntity::class,Settings::class], version = 4, exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun NoteDao():NoteDao
    abstract fun SettingsDao():SettingsDao


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
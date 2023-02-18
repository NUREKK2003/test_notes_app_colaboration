package com.nomteam.csvnotesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nomteam.csvnotesapp.data.db.dao.NoteDao
import com.nomteam.csvnotesapp.data.db.dao.SettingsDao
import com.nomteam.csvnotesapp.data.db.structures.NoteEntity
import com.nomteam.csvnotesapp.data.db.structures.Settings


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
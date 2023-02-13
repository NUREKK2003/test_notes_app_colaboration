package com.example.testnotesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testnotesapp.data.db.dao.NoteDao


@Database(entities = [NoteDatabase::class], version = 1, exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun NoteDao():NoteDao

    companion object{
        @Volatile
        private var INSTANCE:NoteDatabase?=null
        fun getDatabase(context: Context): NoteDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "Note_database"

                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
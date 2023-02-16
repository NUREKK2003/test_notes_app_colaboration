package com.example.testnotesapp.data.repository

import android.content.Context
import com.example.testnotesapp.data.db.NotesDb
import com.example.testnotesapp.data.db.dao.SettingsDao
import com.example.testnotesapp.data.db.structures.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SettingsRepository(context: Context):SettingsDao {
    private val dao = NotesDb.getDatabase(context).SettingsDao()

    override fun getAllSettings(): Flow<List<Settings>> {
        return dao.getAllSettings()
    }

    override suspend fun addSettings(settings: Settings) = withContext(Dispatchers.IO){
        dao.addSettings(settings)
    }

    override suspend fun updateSettings(settings: Settings) = withContext(Dispatchers.IO){
        dao.updateSettings(settings)
    }

    override suspend fun deleteSettings(settings: Settings) = withContext(Dispatchers.IO){
        dao.deleteSettings(settings)
    }

    override suspend fun deleteAllSettings() = withContext(Dispatchers.IO){
        dao.deleteAllSettings()
    }
}
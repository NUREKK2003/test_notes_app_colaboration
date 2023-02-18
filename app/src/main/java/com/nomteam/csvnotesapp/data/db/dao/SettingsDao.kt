package com.nomteam.csvnotesapp.data.db.dao

import androidx.room.*
import com.nomteam.csvnotesapp.data.db.structures.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    // suspend - asynchroniczne
    @Query("SELECT * FROM settings_table")
    fun getAllSettings(): Flow<List<Settings>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSettings(settings:Settings)

    @Update
    suspend fun updateSettings(settings:Settings)

    @Delete
    suspend fun deleteSettings(settings:Settings)

    @Query("DELETE FROM settings_table")
    suspend fun deleteAllSettings()

}
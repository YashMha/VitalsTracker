package com.vitals.tracker.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vitals.tracker.data.model.VitalsRecord

@Dao
interface VitalsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: VitalsRecord): Long

    @Delete
    suspend fun delete(record: VitalsRecord)

    // All vitals for a patient, newest first
    @Query("SELECT * FROM vitals_records WHERE patientId = :patientId ORDER BY timestamp DESC")
    fun getVitalsForPatient(patientId: Long): LiveData<List<VitalsRecord>>

    // Latest single record
    @Query("SELECT * FROM vitals_records WHERE patientId = :patientId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestVital(patientId: Long): LiveData<VitalsRecord?>

    // Records for a specific calendar day
    @Query("""
        SELECT * FROM vitals_records 
        WHERE patientId = :patientId 
          AND timestamp >= :dayStart 
          AND timestamp < :dayEnd 
        ORDER BY timestamp DESC
    """)
    fun getVitalsForDay(patientId: Long, dayStart: Long, dayEnd: Long): LiveData<List<VitalsRecord>>

    // Same as above but using suspend for ViewModel calculations
    @Query("""
        SELECT * FROM vitals_records 
        WHERE patientId = :patientId 
          AND timestamp >= :dayStart 
          AND timestamp < :dayEnd 
        ORDER BY timestamp DESC
    """)
    suspend fun getVitalsForDaySync(patientId: Long, dayStart: Long, dayEnd: Long): List<VitalsRecord>
}

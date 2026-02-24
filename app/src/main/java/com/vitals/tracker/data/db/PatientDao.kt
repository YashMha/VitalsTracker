package com.vitals.tracker.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vitals.tracker.data.model.Patient

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(patient: Patient): Long

    @Update
    suspend fun update(patient: Patient)

    @Delete
    suspend fun delete(patient: Patient)

    @Query("SELECT * FROM patients ORDER BY name ASC")
    fun getAllPatients(): LiveData<List<Patient>>

    @Query("SELECT * FROM patients WHERE id = :id")
    fun getPatientById(id: Long): LiveData<Patient?>

    @Query("SELECT * FROM patients WHERE id = :id")
    suspend fun getPatientByIdSync(id: Long): Patient?

    @Query("SELECT COUNT(*) FROM patients WHERE uhid = :uhid AND id != :excludeId")
    suspend fun countByUhid(uhid: String, excludeId: Long = 0): Int
}

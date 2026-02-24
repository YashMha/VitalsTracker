package com.vitals.tracker.data.repository

import androidx.lifecycle.LiveData
import com.vitals.tracker.data.db.PatientDao
import com.vitals.tracker.data.model.Patient

class PatientRepository(private val dao: PatientDao) {

    val allPatients: LiveData<List<Patient>> = dao.getAllPatients()

    fun getPatient(id: Long): LiveData<Patient?> = dao.getPatientById(id)

    suspend fun addPatient(patient: Patient): Result<Long> {
        // Check if the UHID is unique
        if (dao.countByUhid(patient.uhid) > 0) {
            return Result.failure(IllegalArgumentException("UHID '${patient.uhid}' already exists."))
        }
        return try {
            Result.success(dao.insert(patient))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePatient(patient: Patient) = dao.update(patient)

    suspend fun deletePatient(patient: Patient) = dao.delete(patient)
}

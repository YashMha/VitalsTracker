package com.vitals.tracker.data.repository

import androidx.lifecycle.LiveData
import com.vitals.tracker.data.db.VitalsDao
import com.vitals.tracker.data.model.VitalsRecord
import com.vitals.tracker.data.model.AlertLevel
import com.vitals.tracker.utils.VitalStatusSerializer

class VitalsRepository(private val dao: VitalsDao) {

    fun getVitalsForPatient(patientId: Long): LiveData<List<VitalsRecord>> =
        dao.getVitalsForPatient(patientId)

    fun getLatestVital(patientId: Long): LiveData<VitalsRecord?> =
        dao.getLatestVital(patientId)

    fun getVitalsForDay(patientId: Long, dayStart: Long, dayEnd: Long): LiveData<List<VitalsRecord>> =
        dao.getVitalsForDay(patientId, dayStart, dayEnd)

    suspend fun getVitalsForDaySync(patientId: Long, dayStart: Long, dayEnd: Long): List<VitalsRecord> =
        dao.getVitalsForDaySync(patientId, dayStart, dayEnd)

    suspend fun addVitalsRecord(record: VitalsRecord): Long = dao.insert(record)

    suspend fun deleteRecord(record: VitalsRecord) = dao.delete(record)


     // This Determines the best record of thge day = fewest/lowest severity alerts.
     // Scored: NORMAL=0, WARNING=1, CRITICAL=2
    fun bestRecord(records: List<VitalsRecord>): VitalsRecord? =
        records.minByOrNull { record ->
            val s = VitalStatusSerializer.fromJson(record.vitalStatusJson)
            listOf(s.temperature, s.pulse, s.respiratoryRate, s.spo2, s.systolic, s.diastolic)
                .sumOf { it.score() }
        }

     // This Determines the worst record of day = most/highest severity alerts.
    fun worstRecord(records: List<VitalsRecord>): VitalsRecord? =
        records.maxByOrNull { record ->
            val s = VitalStatusSerializer.fromJson(record.vitalStatusJson)
            listOf(s.temperature, s.pulse, s.respiratoryRate, s.spo2, s.systolic, s.diastolic)
                .sumOf { it.score() }
        }

    private fun AlertLevel.score() = ordinal  // NORMAL=0, WARNING=1, CRITICAL=2
}

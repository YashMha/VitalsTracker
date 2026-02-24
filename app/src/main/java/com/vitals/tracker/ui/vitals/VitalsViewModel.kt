package com.vitals.tracker.ui.vitals

import android.app.Application
import androidx.lifecycle.*
import com.vitals.tracker.data.db.AppDatabase
import com.vitals.tracker.data.model.Patient
import com.vitals.tracker.data.model.VitalsRecord
import com.vitals.tracker.data.repository.VitalsRepository
import com.vitals.tracker.data.repository.PatientRepository
import com.vitals.tracker.rules.RuleEngine
import com.vitals.tracker.utils.VitalStatusSerializer
import kotlinx.coroutines.launch

sealed class VitalsSaveState {
    object Idle : VitalsSaveState()
    data class HardValidationFailed(val fields: List<String>) : VitalsSaveState()
    data class Success(val id: Long) : VitalsSaveState()
    data class Error(val message: String) : VitalsSaveState()
}

class VitalsViewModel(app: Application) : AndroidViewModel(app) {

    private val vitalsRepo = VitalsRepository(AppDatabase.getInstance(app).vitalsDao())
    private val patientRepo = PatientRepository(AppDatabase.getInstance(app).patientDao())

    private val _saveState = MutableLiveData<VitalsSaveState>(VitalsSaveState.Idle)
    val saveState: LiveData<VitalsSaveState> = _saveState

    fun getVitalsForPatient(patientId: Long) = vitalsRepo.getVitalsForPatient(patientId)

    fun saveVitals(
        patient: Patient,
        temperature: Float,
        pulse: Int,
        respiratoryRate: Int,
        spo2: Float,
        systolic: Int,
        diastolic: Int,
        forceOnHardFail: Boolean = false
    ) {
        viewModelScope.launch {
            val hardFails = RuleEngine.hardValidate(
                patient.ageGroup, patient.gender,
                temperature, pulse.toFloat(), respiratoryRate.toFloat(),
                spo2, systolic.toFloat(), diastolic.toFloat()
            )

            if (hardFails.isNotEmpty() && !forceOnHardFail) {
                _saveState.postValue(VitalsSaveState.HardValidationFailed(hardFails))
                return@launch
            }

            val statusMap = RuleEngine.evaluateAll(
                patient.ageGroup, patient.gender,
                temperature, pulse.toFloat(), respiratoryRate.toFloat(),
                spo2, systolic.toFloat(), diastolic.toFloat()
            )

            val record = VitalsRecord(
                patientId = patient.id,
                temperature = temperature,
                pulse = pulse,
                respiratoryRate = respiratoryRate,
                spo2 = spo2,
                systolic = systolic,
                diastolic = diastolic,
                vitalStatusJson = VitalStatusSerializer.toJson(statusMap)
            )

            val id = vitalsRepo.addVitalsRecord(record)
            _saveState.postValue(VitalsSaveState.Success(id))
        }
    }

    fun resetSaveState() { _saveState.value = VitalsSaveState.Idle }
}

package com.vitals.tracker.ui.patient

import android.app.Application
import androidx.lifecycle.*
import com.vitals.tracker.data.db.AppDatabase
import com.vitals.tracker.data.model.Gender
import com.vitals.tracker.data.model.Patient
import com.vitals.tracker.data.repository.PatientRepository
import kotlinx.coroutines.launch

class PatientViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = PatientRepository(AppDatabase.getInstance(app).patientDao())

    val allPatients: LiveData<List<Patient>> = repo.allPatients

    // Search + filter state
    private val _searchQuery = MutableLiveData("")
    private val _filterGender = MutableLiveData<Gender?>(null)
    private val _filterAgeMin = MutableLiveData<Int?>(null)
    private val _filterAgeMax = MutableLiveData<Int?>(null)

    val filteredPatients: LiveData<List<Patient>> = MediatorLiveData<List<Patient>>().apply {
        fun update() {
            val list    = allPatients.value ?: return
            val query   = _searchQuery.value?.trim().orEmpty().lowercase()
            val gender  = _filterGender.value
            val ageMin  = _filterAgeMin.value
            val ageMax  = _filterAgeMax.value

            value = list.filter { p ->
                (query.isEmpty() || p.name.lowercase().contains(query)) &&
                        (gender == null  || p.gender == gender) &&
                        (ageMin == null  || p.age >= ageMin) &&
                        (ageMax == null  || p.age <= ageMax)
            }
        }
        addSource(allPatients)    { update() }
        addSource(_searchQuery)   { update() }
        addSource(_filterGender)  { update() }
        addSource(_filterAgeMin)  { update() }
        addSource(_filterAgeMax)  { update() }
    }

    fun setSearchQuery(q: String)       { _searchQuery.value = q }
    fun setGenderFilter(g: Gender?)     { _filterGender.value = g }
    fun setAgeRange(min: Int?, max: Int?) {
        _filterAgeMin.value = min
        _filterAgeMax.value = max
    }
    fun clearFilters() {
        _searchQuery.value  = ""
        _filterGender.value = null
        _filterAgeMin.value = null
        _filterAgeMax.value = null
    }

    fun getPatient(id: Long): LiveData<Patient?> = repo.getPatient(id)

    private val _saveResult = MutableLiveData<Result<Long>>()
    val saveResult: LiveData<Result<Long>> = _saveResult

    fun addPatient(uhid: String, name: String, gender: Gender, age: Int, mobile: String) {
        viewModelScope.launch {
            val patient = Patient(
                uhid = uhid.trim(), name = name.trim(),
                gender = gender, age = age, mobile = mobile.trim()
            )
            _saveResult.postValue(repo.addPatient(patient))
        }
    }

    fun deletePatient(patient: Patient) {
        viewModelScope.launch { repo.deletePatient(patient) }
    }
}
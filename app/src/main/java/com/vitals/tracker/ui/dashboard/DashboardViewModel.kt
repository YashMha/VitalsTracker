package com.vitals.tracker.ui.dashboard

import android.app.Application
import androidx.lifecycle.*
import com.vitals.tracker.data.db.AppDatabase
import com.vitals.tracker.data.model.VitalsRecord
import com.vitals.tracker.data.repository.VitalsRepository
import com.vitals.tracker.utils.toDayEnd
import com.vitals.tracker.utils.toDayStart
import kotlinx.coroutines.launch

data class DashboardData(
    val latest: VitalsRecord?,
    val best: VitalsRecord?,
    val worst: VitalsRecord?,
    val allToday: List<VitalsRecord>
)

class DashboardViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = VitalsRepository(AppDatabase.getInstance(app).vitalsDao())

    private val _dashboard = MutableLiveData<DashboardData>()
    val dashboard: LiveData<DashboardData> = _dashboard

    fun loadDashboard(patientId: Long) {
        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val todayRecords = repo.getVitalsForDaySync(patientId, now.toDayStart(), now.toDayEnd())

            _dashboard.postValue(
                DashboardData(
                    latest = todayRecords.firstOrNull(),
                    best = repo.bestRecord(todayRecords),
                    worst = repo.worstRecord(todayRecords),
                    allToday = todayRecords
                )
            )
        }
    }
}

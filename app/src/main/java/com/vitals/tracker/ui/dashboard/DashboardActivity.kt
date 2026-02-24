package com.vitals.tracker.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vitals.tracker.data.model.AlertLevel
import com.vitals.tracker.data.model.VitalsRecord
import com.vitals.tracker.databinding.ActivityDashboardBinding
import com.vitals.tracker.ui.patient.PatientListActivity.Companion.EXTRA_PATIENT_ID
import com.vitals.tracker.utils.VitalStatusSerializer
import com.vitals.tracker.utils.toColorInt
import com.vitals.tracker.utils.toDisplayDateTime

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val patientId = intent.getLongExtra(EXTRA_PATIENT_ID, -1L)
        if (patientId == -1L) { finish(); return }

        viewModel.dashboard.observe(this) { data ->
            if (data.allToday.isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
                binding.scrollContent.visibility = View.GONE
                return@observe
            }
            binding.tvNoData.visibility = View.GONE
            binding.scrollContent.visibility = View.VISIBLE

            data.latest?.let { populateSection(binding.sectionLatest, it, "Latest Reading") }
            data.best?.let   { populateSection(binding.sectionBest,   it, "Best of Today") }
            data.worst?.let  { populateSection(binding.sectionWorst,  it, "Worst of Today") }
        }

        viewModel.loadDashboard(patientId)
    }

    private fun populateSection(section: com.vitals.tracker.databinding.LayoutVitalsSectionBinding, record: VitalsRecord, label: String) {
        val statusMap = VitalStatusSerializer.fromJson(record.vitalStatusJson)

        section.tvSectionTitle.text = label
        section.tvTimestamp.text = record.timestamp.toDisplayDateTime()

        section.tvTemp.text = "🌡 Temp: ${record.temperature}°F"
        section.tvTempStatus.text = statusMap.temperature.name
        section.tvTempStatus.setTextColor(statusMap.temperature.toColorInt())

        section.tvPulse.text = "💓 Pulse: ${record.pulse} bpm"
        section.tvPulseStatus.text = statusMap.pulse.name
        section.tvPulseStatus.setTextColor(statusMap.pulse.toColorInt())

        section.tvRr.text = "🫁 RR: ${record.respiratoryRate} br/min"
        section.tvRrStatus.text = statusMap.respiratoryRate.name
        section.tvRrStatus.setTextColor(statusMap.respiratoryRate.toColorInt())

        section.tvSpo2.text = "🩸 SpO₂: ${record.spo2}%"
        section.tvSpo2Status.text = statusMap.spo2.name
        section.tvSpo2Status.setTextColor(statusMap.spo2.toColorInt())

        section.tvBp.text = "💊 BP: ${record.systolic}/${record.diastolic} mmHg"
        val bpOverall = maxOf(statusMap.systolic, statusMap.diastolic)
        section.tvBpStatus.text = bpOverall.name
        section.tvBpStatus.setTextColor(bpOverall.toColorInt())

        val overall = statusMap.overall
        section.tvOverall.text = "Overall: ${overall.name}"
        section.tvOverall.setTextColor(overall.toColorInt())
        section.cardSection.setCardBackgroundColor(
            when (overall) {
                AlertLevel.CRITICAL -> 0xFFFFF3F3.toInt()
                AlertLevel.WARNING  -> 0xFFFFF8ED.toInt()
                else                -> 0xFFF3FFF3.toInt()
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean { onBackPressedDispatcher.onBackPressed(); return true }
}

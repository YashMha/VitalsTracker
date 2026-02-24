package com.vitals.tracker.ui.patient

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vitals.tracker.R
import com.vitals.tracker.data.model.AlertLevel
import com.vitals.tracker.data.model.VitalsRecord
import com.vitals.tracker.databinding.ActivityPatientDetailBinding
import com.vitals.tracker.ui.dashboard.DashboardActivity
import com.vitals.tracker.ui.patient.PatientListActivity.Companion.EXTRA_PATIENT_ID
import com.vitals.tracker.ui.vitals.AddVitalsActivity
import com.vitals.tracker.utils.VitalStatusSerializer
import com.vitals.tracker.utils.toColorInt
import com.vitals.tracker.utils.toDisplayDateTime

class PatientDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientDetailBinding
    private val viewModel: PatientViewModel by viewModels()
    private var patientId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        patientId = intent.getLongExtra(EXTRA_PATIENT_ID, -1L)
        if (patientId == -1L) { finish(); return }

        viewModel.getPatient(patientId).observe(this) { patient ->
            patient ?: return@observe
            binding.tvPatientName.text = patient.name
            binding.tvPatientUhid.text = "UHID: ${patient.uhid} | UUID: ${patient.uuid.take(8)}…"
            binding.tvPatientMeta.text = "${patient.gender.name} • ${patient.age} yrs • ${patient.ageGroup.name}"
            if (patient.mobile.isNotEmpty()) binding.tvMobile.text = "📞  ${patient.mobile}"
        }

        val adapter = VitalsHistoryAdapter()
        binding.rvVitalsHistory.layoutManager = LinearLayoutManager(this)
        binding.rvVitalsHistory.adapter = adapter

        // Load vitals from VitalsViewModel via a separate ViewModel (or reuse)
        val vitalsVm: com.vitals.tracker.ui.vitals.VitalsViewModel by viewModels()
        vitalsVm.getVitalsForPatient(patientId).observe(this) { records ->
            adapter.submitList(records)
            binding.tvNoVitals.visibility = if (records.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.btnAddVitals.setOnClickListener {
            startActivity(Intent(this, AddVitalsActivity::class.java)
                .putExtra(EXTRA_PATIENT_ID, patientId))
        }

        binding.btnDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java)
                .putExtra(EXTRA_PATIENT_ID, patientId))
        }
    }

    override fun onSupportNavigateUp(): Boolean { onBackPressedDispatcher.onBackPressed(); return true }
}

// Vitals History Adapter
class VitalsHistoryAdapter : RecyclerView.Adapter<VitalsHistoryAdapter.VH>() {

    private var list: List<VitalsRecord> = emptyList()

    fun submitList(newList: List<VitalsRecord>) {
        list = newList; notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vitals, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(list[position])
    override fun getItemCount() = list.size

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)
        private val tvVitals: TextView    = view.findViewById(R.id.tvVitals)
        private val tvStatus: TextView    = view.findViewById(R.id.tvOverallStatus)
        private val card: CardView        = view.findViewById(R.id.cardVitals)

        fun bind(record: VitalsRecord) {
            tvTimestamp.text = record.timestamp.toDisplayDateTime()
            tvVitals.text = """
                Temp: ${record.temperature}°F  •  Pulse: ${record.pulse} bpm
                RR: ${record.respiratoryRate} br/min  •  SpO₂: ${record.spo2}%
                BP: ${record.systolic}/${record.diastolic} mmHg
            """.trimIndent()
            val statusMap = VitalStatusSerializer.fromJson(record.vitalStatusJson)
            val overall   = statusMap.overall
            tvStatus.text = overall.name
            tvStatus.setTextColor(overall.toColorInt())
            card.setCardBackgroundColor(
                when (overall) {
                    AlertLevel.CRITICAL -> 0xFFFFF3F3.toInt()
                    AlertLevel.WARNING  -> 0xFFFFFAF0.toInt()
                    else                -> 0xFFFFFFFF.toInt()
                }
            )
        }
    }
}

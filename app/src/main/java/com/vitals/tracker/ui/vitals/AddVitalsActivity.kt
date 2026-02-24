package com.vitals.tracker.ui.vitals

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.vitals.tracker.data.db.AppDatabase
import com.vitals.tracker.data.model.Patient
import com.vitals.tracker.databinding.ActivityAddVitalsBinding
import com.vitals.tracker.ui.patient.PatientListActivity.Companion.EXTRA_PATIENT_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddVitalsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddVitalsBinding
    private val viewModel: VitalsViewModel by viewModels()
    private var patient: Patient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVitalsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val patientId = intent.getLongExtra(EXTRA_PATIENT_ID, -1L)
        if (patientId == -1L) { finish(); return }

        // Load patient synchronously for rule engine context
        CoroutineScope(Dispatchers.IO).launch {
            patient = AppDatabase.getInstance(applicationContext)
                .patientDao().getPatientByIdSync(patientId)
            withContext(Dispatchers.Main) {
                patient?.let { p ->
                    supportActionBar?.subtitle = "Patient: ${p.name}"
                    binding.tvPatientContext.text =
                        "${p.gender.name} • ${p.age} yrs (${p.ageGroup.name})"
                }
            }
        }

        viewModel.saveState.observe(this) { state ->
            when (state) {
                is VitalsSaveState.Idle -> Unit

                is VitalsSaveState.HardValidationFailed -> {
                    AlertDialog.Builder(this)
                        .setTitle("⚠ Physiologically Unusual Values")
                        .setMessage(
                            "The following fields have values outside physiologically possible ranges:\n\n" +
                            state.fields.joinToString("\n") { "• $it" } +
                            "\n\nDo you want to save anyway?"
                        )
                        .setPositiveButton("Save Anyway") { _, _ ->
                            submitVitals(force = true)
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            viewModel.resetSaveState()
                        }
                        .show()
                }

                is VitalsSaveState.Success -> {
                    Toast.makeText(this, "Vitals recorded successfully!", Toast.LENGTH_SHORT).show()
                    viewModel.resetSaveState()
                    finish()
                }

                is VitalsSaveState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                    viewModel.resetSaveState()
                }
            }
        }

        binding.btnSaveVitals.setOnClickListener { submitVitals(force = false) }
    }

    private fun submitVitals(force: Boolean) {
        val p = patient ?: run {
            Toast.makeText(this, "Patient data not loaded yet", Toast.LENGTH_SHORT).show()
            return
        }

        val tempStr = binding.etTemperature.text.toString()
        val pulseStr = binding.etPulse.text.toString()
        val rrStr    = binding.etRespRate.text.toString()
        val spo2Str  = binding.etSpo2.text.toString()
        val sysStr   = binding.etSystolic.text.toString()
        val diaStr   = binding.etDiastolic.text.toString()

        // Basic empty checks
        if (tempStr.isEmpty())  { binding.etTemperature.error = "Required"; return }
        if (pulseStr.isEmpty()) { binding.etPulse.error = "Required"; return }
        if (rrStr.isEmpty())    { binding.etRespRate.error = "Required"; return }
        if (spo2Str.isEmpty())  { binding.etSpo2.error = "Required"; return }
        if (sysStr.isEmpty())   { binding.etSystolic.error = "Required"; return }
        if (diaStr.isEmpty())   { binding.etDiastolic.error = "Required"; return }

        val temp  = tempStr.toFloatOrNull()  ?: run { binding.etTemperature.error = "Invalid"; return }
        val pulse = pulseStr.toIntOrNull()   ?: run { binding.etPulse.error = "Invalid"; return }
        val rr    = rrStr.toIntOrNull()      ?: run { binding.etRespRate.error = "Invalid"; return }
        val spo2  = spo2Str.toFloatOrNull()  ?: run { binding.etSpo2.error = "Invalid"; return }
        val sys   = sysStr.toIntOrNull()     ?: run { binding.etSystolic.error = "Invalid"; return }
        val dia   = diaStr.toIntOrNull()     ?: run { binding.etDiastolic.error = "Invalid"; return }

        viewModel.saveVitals(p, temp, pulse, rr, spo2, sys, dia, forceOnHardFail = force)
    }

    override fun onSupportNavigateUp(): Boolean { onBackPressedDispatcher.onBackPressed(); return true }
}

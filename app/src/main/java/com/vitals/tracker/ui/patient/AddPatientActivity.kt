package com.vitals.tracker.ui.patient

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vitals.tracker.data.model.Gender
import com.vitals.tracker.databinding.ActivityAddPatientBinding

class AddPatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPatientBinding
    private val viewModel: PatientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.saveResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Patient added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            result.onFailure { e ->
                Toast.makeText(this, e.message ?: "Error saving patient", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnSave.setOnClickListener { validateAndSave() }
    }

    private fun validateAndSave() {
        val uhid   = binding.etUhid.text.toString().trim()
        val name   = binding.etName.text.toString().trim()
        val ageStr = binding.etAge.text.toString().trim()
        val mobile = binding.etMobile.text.toString().trim()

        if (uhid.isEmpty()) { binding.etUhid.error = "Required"; return }
        if (name.isEmpty()) { binding.etName.error = "Required"; return }
        if (ageStr.isEmpty()) { binding.etAge.error = "Required"; return }

        val age = ageStr.toIntOrNull()
        if (age == null || age < 0 || age > 150) {
            binding.etAge.error = "Enter a valid age (0–150)"
            return
        }

        val gender = if (binding.rbMale.isChecked) Gender.MALE else Gender.FEMALE

        viewModel.addPatient(uhid, name, gender, age, mobile)
    }

    override fun onSupportNavigateUp(): Boolean { onBackPressedDispatcher.onBackPressed(); return true }
}

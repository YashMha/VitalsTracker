package com.vitals.tracker.ui.patient

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vitals.tracker.R
import com.vitals.tracker.data.model.Gender
import com.vitals.tracker.data.model.Patient
import com.vitals.tracker.databinding.ActivityPatientListBinding

class PatientListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPatientListBinding
    private val viewModel: PatientViewModel by viewModels()
    private lateinit var adapter: PatientAdapter

    // Active filter state (for UI toggle)
    private var activeGender: Gender? = null
    private var activeAgeMin: Int? = null
    private var activeAgeMax: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        adapter = PatientAdapter { patient ->
            startActivity(
                Intent(this, PatientDetailActivity::class.java)
                    .putExtra(EXTRA_PATIENT_ID, patient.id)
            )
        }

        binding.rvPatients.layoutManager = LinearLayoutManager(this)
        binding.rvPatients.adapter = adapter

        // Observe filtered list
        viewModel.filteredPatients.observe(this) { patients ->
            adapter.submitList(patients)
            binding.tvEmpty.visibility = if (patients.isEmpty()) View.VISIBLE else View.GONE
        }

        // Search
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setSearchQuery(newText ?: "")
                return true
            }
        })

        // Filter button
        binding.btnFilter.setOnClickListener { showFilterDialog() }

        // Clear filters
        binding.btnClearFilter.setOnClickListener {
            activeGender = null
            activeAgeMin = null
            activeAgeMax = null
            viewModel.clearFilters()
            binding.btnClearFilter.visibility = View.GONE
            binding.tvActiveFilter.visibility = View.GONE
        }

        // FAB
        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddPatientActivity::class.java))
        }
    }

    private fun showFilterDialog() {
        val genderOptions = arrayOf("Any", "Male", "Female")
        val ageRanges = arrayOf("Any age", "0–11 (Child)", "12–17", "18–40", "41–60", "60+")

        var selectedGenderIdx = when (activeGender) {
            Gender.MALE   -> 1
            Gender.FEMALE -> 2
            else          -> 0
        }
        var selectedAgeIdx = when {
            activeAgeMin == 0  && activeAgeMax == 11   -> 1
            activeAgeMin == 12 && activeAgeMax == 17   -> 2
            activeAgeMin == 18 && activeAgeMax == 40   -> 3
            activeAgeMin == 41 && activeAgeMax == 60   -> 4
            activeAgeMin == 61 && activeAgeMax == null -> 5
            else -> 0
        }

        // Step 1: pick gender
        AlertDialog.Builder(this)
            .setTitle("Filter by Gender")
            .setSingleChoiceItems(genderOptions, selectedGenderIdx) { _: android.content.DialogInterface, which: Int ->
                selectedGenderIdx = which
            }
            .setPositiveButton("Next") { _: android.content.DialogInterface, _: Int ->
                // Step 2: pick age range
                AlertDialog.Builder(this)
                    .setTitle("Filter by Age Range")
                    .setSingleChoiceItems(ageRanges, selectedAgeIdx) { _: android.content.DialogInterface, which: Int ->
                        selectedAgeIdx = which
                    }
                    .setPositiveButton("Apply") { _: android.content.DialogInterface, _: Int ->
                        activeGender = when (selectedGenderIdx) {
                            1    -> Gender.MALE
                            2    -> Gender.FEMALE
                            else -> null
                        }
                        val (minAge, maxAge) = when (selectedAgeIdx) {
                            1    -> Pair(0, 11)
                            2    -> Pair(12, 17)
                            3    -> Pair(18, 40)
                            4    -> Pair(41, 60)
                            5    -> Pair(61, null)
                            else -> Pair(null, null)
                        }
                        activeAgeMin = minAge
                        activeAgeMax = maxAge

                        viewModel.setGenderFilter(activeGender)
                        viewModel.setAgeRange(activeAgeMin, activeAgeMax)

                        val hasFilter = activeGender != null || activeAgeMin != null
                        binding.btnClearFilter.visibility = if (hasFilter) View.VISIBLE else View.GONE
                        binding.tvActiveFilter.visibility = if (hasFilter) View.VISIBLE else View.GONE
                        if (hasFilter) {
                            val parts = mutableListOf<String>()
                            activeGender?.let { parts += it.name }
                            if (activeAgeMin != null) parts += "Age: ${activeAgeMin}–${activeAgeMax ?: "∞"}"
                            binding.tvActiveFilter.text = "Filters: ${parts.joinToString(", ")}"
                        }
                    }
                    .setNegativeButton("Back", null)
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    companion object {
        const val EXTRA_PATIENT_ID = "extra_patient_id"
    }
}

// Adapter
class PatientAdapter(
    private val onClick: (Patient) -> Unit
) : RecyclerView.Adapter<PatientAdapter.VH>() {

    private var list: List<Patient> = emptyList()

    fun submitList(newList: List<Patient>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_patient, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(list[position])
    override fun getItemCount() = list.size

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName: TextView = view.findViewById(R.id.tvPatientName)
        private val tvUhid: TextView = view.findViewById(R.id.tvUhid)
        private val tvMeta: TextView = view.findViewById(R.id.tvMeta)

        fun bind(patient: Patient) {
            tvName.text = patient.name
            tvUhid.text = "UHID: ${patient.uhid}"
            tvMeta.text = "${patient.gender.name} • ${patient.age} yrs"
            itemView.setOnClickListener { onClick(patient) }
        }
    }
}
package com.vitals.tracker.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

enum class AlertLevel { NORMAL, WARNING, CRITICAL }

data class VitalStatusMap(
    val temperature: AlertLevel = AlertLevel.NORMAL,
    val pulse: AlertLevel = AlertLevel.NORMAL,
    val respiratoryRate: AlertLevel = AlertLevel.NORMAL,
    val spo2: AlertLevel = AlertLevel.NORMAL,
    val systolic: AlertLevel = AlertLevel.NORMAL,
    val diastolic: AlertLevel = AlertLevel.NORMAL
) {
    val overall: AlertLevel
        get() = listOf(temperature, pulse, respiratoryRate, spo2, systolic, diastolic)
            .maxByOrNull { it.ordinal } ?: AlertLevel.NORMAL
}

@Entity(
    tableName = "vitals_records",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("patientId")]
)
data class VitalsRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val patientId: Long,
    val timestamp: Long = System.currentTimeMillis(),

    val temperature: Float,
    val pulse: Int,
    val respiratoryRate: Int,
    val spo2: Float,
    val systolic: Int,
    val diastolic: Int,

    val vitalStatusJson: String = "{}"
)

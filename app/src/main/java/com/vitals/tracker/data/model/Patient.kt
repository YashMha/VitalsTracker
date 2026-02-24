package com.vitals.tracker.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

enum class Gender { MALE, FEMALE }

@Entity(
    tableName = "patients",
    indices = [
        Index(value = ["uhid"], unique = true),
        Index(value = ["uuid"], unique = true)
    ]
)
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val uhid: String,
    val uuid: String = UUID.randomUUID().toString(),
    val name: String,
    val gender: Gender,
    val age: Int,
    val mobile: String = ""
) {
    val ageGroup: AgeGroup
        get() = if (age < 12) AgeGroup.CHILD else AgeGroup.ADULT
}

enum class AgeGroup { CHILD, ADULT }

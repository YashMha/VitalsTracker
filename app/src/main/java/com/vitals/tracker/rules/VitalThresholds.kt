package com.vitals.tracker.rules

import com.vitals.tracker.data.model.AgeGroup
import com.vitals.tracker.data.model.Gender

object VitalThresholds {

    // Temp (F)
    private val tempChildMale = ThresholdRule(
        hardMin = 90f, hardMax = 108f,
        warnLow = 97.5f, warnHigh = 99.5f,
        critLow = 95f, critHigh = 103f
    )
    private val tempChildFemale = ThresholdRule(
        hardMin = 90f, hardMax = 108f,
        warnLow = 97.5f, warnHigh = 99.5f,
        critLow = 95f, critHigh = 103f
    )
    private val tempAdultMale = ThresholdRule(
        hardMin = 90f, hardMax = 108f,
        warnLow = 97.8f, warnHigh = 99.1f,
        critLow = 95f, critHigh = 103f
    )
    private val tempAdultFemale = ThresholdRule(
        hardMin = 90f, hardMax = 108f,
        warnLow = 97.8f, warnHigh = 99.1f,
        critLow = 95f, critHigh = 103f
    )

    // Pulse (BPM)
    private val pulseChildMale = ThresholdRule(
        hardMin = 30f, hardMax = 250f,
        warnLow = 70f, warnHigh = 120f,
        critLow = 50f, critHigh = 160f
    )
    private val pulseChildFemale = ThresholdRule(
        hardMin = 30f, hardMax = 250f,
        warnLow = 70f, warnHigh = 120f,
        critLow = 50f, critHigh = 160f
    )
    private val pulseAdultMale = ThresholdRule(
        hardMin = 20f, hardMax = 250f,
        warnLow = 60f, warnHigh = 100f,
        critLow = 40f, critHigh = 140f
    )
    private val pulseAdultFemale = ThresholdRule(
        hardMin = 20f, hardMax = 250f,
        warnLow = 60f, warnHigh = 100f,
        critLow = 40f, critHigh = 140f
    )

    // Respiratory Rate (breaths/min)
    private val rrChildMale = ThresholdRule(
        hardMin = 5f, hardMax = 80f,
        warnLow = 20f, warnHigh = 40f,
        critLow = 10f, critHigh = 60f
    )
    private val rrChildFemale = ThresholdRule(
        hardMin = 5f, hardMax = 80f,
        warnLow = 20f, warnHigh = 40f,
        critLow = 10f, critHigh = 60f
    )
    private val rrAdultMale = ThresholdRule(
        hardMin = 4f, hardMax = 60f,
        warnLow = 12f, warnHigh = 20f,
        critLow = 8f, critHigh = 30f
    )
    private val rrAdultFemale = ThresholdRule(
        hardMin = 4f, hardMax = 60f,
        warnLow = 12f, warnHigh = 20f,
        critLow = 8f, critHigh = 30f
    )

    // SpO2 (%)
    private val spo2All = ThresholdRule(
        hardMin = 0f, hardMax = 100f,
        warnLow = 94f, warnHigh = 100f,   // 94-100 = normal
        critLow = 90f, critHigh = 100f    // <90 = critical
    )

    // Systolic BP (mmHg)

    private val sysBpChildMale = ThresholdRule(
        hardMin = 40f, hardMax = 200f,
        warnLow = 85f, warnHigh = 115f,
        critLow = 60f, critHigh = 140f
    )
    private val sysBpChildFemale = ThresholdRule(
        hardMin = 40f, hardMax = 200f,
        warnLow = 85f, warnHigh = 110f,
        critLow = 60f, critHigh = 140f
    )
    private val sysBpAdultMale = ThresholdRule(
        hardMin = 40f, hardMax = 250f,
        warnLow = 90f, warnHigh = 130f,
        critLow = 70f, critHigh = 180f
    )
    private val sysBpAdultFemale = ThresholdRule(
        hardMin = 40f, hardMax = 250f,
        warnLow = 90f, warnHigh = 130f,
        critLow = 70f, critHigh = 180f
    )

    // Diastolic BP (mmHg)

    private val diaBpChildMale = ThresholdRule(
        hardMin = 20f, hardMax = 150f,
        warnLow = 50f, warnHigh = 75f,
        critLow = 30f, critHigh = 100f
    )
    private val diaBpChildFemale = ThresholdRule(
        hardMin = 20f, hardMax = 150f,
        warnLow = 50f, warnHigh = 75f,
        critLow = 30f, critHigh = 100f
    )
    private val diaBpAdultMale = ThresholdRule(
        hardMin = 20f, hardMax = 150f,
        warnLow = 60f, warnHigh = 85f,
        critLow = 40f, critHigh = 120f
    )
    private val diaBpAdultFemale = ThresholdRule(
        hardMin = 20f, hardMax = 150f,
        warnLow = 60f, warnHigh = 80f,
        critLow = 40f, critHigh = 120f
    )

    // Lookup API

    fun temperature(ageGroup: AgeGroup, gender: Gender): ThresholdRule = when {
        ageGroup == AgeGroup.CHILD && gender == Gender.MALE   -> tempChildMale
        ageGroup == AgeGroup.CHILD && gender == Gender.FEMALE -> tempChildFemale
        ageGroup == AgeGroup.ADULT && gender == Gender.MALE   -> tempAdultMale
        else                                                   -> tempAdultFemale
    }

    fun pulse(ageGroup: AgeGroup, gender: Gender): ThresholdRule = when {
        ageGroup == AgeGroup.CHILD && gender == Gender.MALE   -> pulseChildMale
        ageGroup == AgeGroup.CHILD && gender == Gender.FEMALE -> pulseChildFemale
        ageGroup == AgeGroup.ADULT && gender == Gender.MALE   -> pulseAdultMale
        else                                                   -> pulseAdultFemale
    }

    fun respiratoryRate(ageGroup: AgeGroup, gender: Gender): ThresholdRule = when {
        ageGroup == AgeGroup.CHILD && gender == Gender.MALE   -> rrChildMale
        ageGroup == AgeGroup.CHILD && gender == Gender.FEMALE -> rrChildFemale
        ageGroup == AgeGroup.ADULT && gender == Gender.MALE   -> rrAdultMale
        else                                                   -> rrAdultFemale
    }

    fun spo2(ageGroup: AgeGroup, gender: Gender): ThresholdRule = spo2All

    fun systolic(ageGroup: AgeGroup, gender: Gender): ThresholdRule = when {
        ageGroup == AgeGroup.CHILD && gender == Gender.MALE   -> sysBpChildMale
        ageGroup == AgeGroup.CHILD && gender == Gender.FEMALE -> sysBpChildFemale
        ageGroup == AgeGroup.ADULT && gender == Gender.MALE   -> sysBpAdultMale
        else                                                   -> sysBpAdultFemale
    }

    fun diastolic(ageGroup: AgeGroup, gender: Gender): ThresholdRule = when {
        ageGroup == AgeGroup.CHILD && gender == Gender.MALE   -> diaBpChildMale
        ageGroup == AgeGroup.CHILD && gender == Gender.FEMALE -> diaBpChildFemale
        ageGroup == AgeGroup.ADULT && gender == Gender.MALE   -> diaBpAdultMale
        else                                                   -> diaBpAdultFemale
    }
}

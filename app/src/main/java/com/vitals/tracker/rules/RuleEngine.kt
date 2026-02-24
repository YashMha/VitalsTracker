package com.vitals.tracker.rules

import com.vitals.tracker.data.model.AgeGroup
import com.vitals.tracker.data.model.AlertLevel
import com.vitals.tracker.data.model.Gender
import com.vitals.tracker.data.model.VitalStatusMap

object RuleEngine {

    fun evaluate(value: Float, rule: ThresholdRule): Pair<AlertLevel, Boolean> {
        val hardFail = value < rule.hardMin || value > rule.hardMax
        val level = when {
            value < rule.critLow || value > rule.critHigh -> AlertLevel.CRITICAL
            value < rule.warnLow || value > rule.warnHigh -> AlertLevel.WARNING
            else                                          -> AlertLevel.NORMAL
        }
        return Pair(level, hardFail)
    }

    fun evaluateAll(
        ageGroup: AgeGroup,
        gender: Gender,
        temperature: Float,
        pulse: Float,
        respiratoryRate: Float,
        spo2: Float,
        systolic: Float,
        diastolic: Float
    ): VitalStatusMap {
        val (tempLevel, _) = evaluate(temperature,VitalThresholds.temperature(ageGroup, gender))
        val (pulseLevel, _) = evaluate(pulse,VitalThresholds.pulse(ageGroup, gender))
        val (rrLevel, _) = evaluate(respiratoryRate,VitalThresholds.respiratoryRate(ageGroup, gender))
        val (spo2Level, _) = evaluate(spo2,VitalThresholds.spo2(ageGroup, gender))
        val (sysLevel, _) = evaluate(systolic,VitalThresholds.systolic(ageGroup, gender))
        val (diaLevel, _) = evaluate(diastolic,VitalThresholds.diastolic(ageGroup, gender))

        return VitalStatusMap(
            temperature = tempLevel,
            pulse = pulseLevel,
            respiratoryRate = rrLevel,
            spo2 = spo2Level,
            systolic = sysLevel,
            diastolic = diaLevel
        )
    }

    fun hardValidate(
        ageGroup: AgeGroup,
        gender: Gender,
        temperature: Float,
        pulse: Float,
        respiratoryRate: Float,
        spo2: Float,
        systolic: Float,
        diastolic: Float
    ): List<String> {
        val failures = mutableListOf<String>()
        if (evaluate(temperature,VitalThresholds.temperature(ageGroup, gender)).second) failures += "Temperature"
        if (evaluate(pulse,VitalThresholds.pulse(ageGroup, gender)).second) failures += "Pulse"
        if (evaluate(respiratoryRate,VitalThresholds.respiratoryRate(ageGroup, gender)).second) failures += "Respiratory Rate"
        if (evaluate(spo2,VitalThresholds.spo2(ageGroup, gender)).second) failures += "SpO₂"
        if (evaluate(systolic,VitalThresholds.systolic(ageGroup, gender)).second) failures += "Systolic BP"
        if (evaluate(diastolic,VitalThresholds.diastolic(ageGroup, gender)).second) failures += "Diastolic BP"
        return failures
    }
}

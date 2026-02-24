package com.vitals.tracker.rules

data class ThresholdRule(
    val hardMin: Float,
    val hardMax: Float,
    val warnLow: Float,
    val warnHigh: Float,
    val critLow: Float,
    val critHigh: Float
)

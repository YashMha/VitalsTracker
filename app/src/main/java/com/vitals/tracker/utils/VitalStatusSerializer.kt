package com.vitals.tracker.utils

import com.vitals.tracker.data.model.AlertLevel
import com.vitals.tracker.data.model.VitalStatusMap
import org.json.JSONObject

object VitalStatusSerializer {

    fun toJson(map: VitalStatusMap): String = JSONObject().apply {
        put("temperature",map.temperature.name)
        put("pulse",map.pulse.name)
        put("respiratoryRate",map.respiratoryRate.name)
        put("spo2",map.spo2.name)
        put("systolic",map.systolic.name)
        put("diastolic",map.diastolic.name)
    }.toString()

    fun fromJson(json: String): VitalStatusMap {
        return try {
            val obj = JSONObject(json)
            VitalStatusMap(
                temperature = AlertLevel.valueOf(obj.optString("temperature",AlertLevel.NORMAL.name)),
                pulse = AlertLevel.valueOf(obj.optString("pulse",AlertLevel.NORMAL.name)),
                respiratoryRate = AlertLevel.valueOf(obj.optString("respiratoryRate",AlertLevel.NORMAL.name)),
                spo2 = AlertLevel.valueOf(obj.optString("spo2",AlertLevel.NORMAL.name)),
                systolic = AlertLevel.valueOf(obj.optString("systolic",AlertLevel.NORMAL.name)),
                diastolic = AlertLevel.valueOf(obj.optString("diastolic",AlertLevel.NORMAL.name))
            )
        } catch (e: Exception) {
            VitalStatusMap()
        }
    }
}

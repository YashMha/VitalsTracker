package com.vitals.tracker.data.db

import androidx.room.TypeConverter
import com.vitals.tracker.data.model.Gender

class Converters {
    @TypeConverter
    fun fromGender(gender: Gender): String = gender.name

    @TypeConverter
    fun toGender(value: String): Gender = Gender.valueOf(value)
}

package com.example.workinghours.infrastructure.database

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.LocalDate

class TypeConverter {
    @TypeConverter
    fun toDate(value: Long): DateTime = DateTime(value)

    @TypeConverter
    fun fromDate(date: DateTime): Long = date.millis

    @TypeConverter
    fun toLocalDate(value: Long): LocalDate = LocalDate(value)

    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long = date.toDateTimeAtStartOfDay().toInstant().millis
}
package com.example.workinghours.infrastructure.database

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

class TypeConverter {
    @TypeConverter
    fun toDate(value: Long): DateTime = DateTime(value)

    @TypeConverter
    fun fromDate(date: DateTime): Long = date.millis

    @TypeConverter
    fun toLocalDate(value: Long): LocalDate = LocalDate(value)

    @TypeConverter
    fun fromLocalDate(date: LocalDate): Long = date.toDateTimeAtStartOfDay().toInstant().millis

    @TypeConverter
    fun toLocalTime(value: Long): LocalTime = LocalTime(value)

    @TypeConverter
    fun fromLocalTime(date: LocalTime): Long = date.toDateTimeToday().toInstant().millis
}
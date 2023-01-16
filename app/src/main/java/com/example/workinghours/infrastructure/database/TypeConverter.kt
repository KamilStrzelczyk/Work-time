package com.example.workinghours.infrastructure.database

import androidx.room.TypeConverter
import org.joda.time.DateTime

class TypeConverter {
    @TypeConverter
    fun toDate(value: Long): DateTime = DateTime(value)


    @TypeConverter
    fun fromDate(date: DateTime): Long = date.millis
}
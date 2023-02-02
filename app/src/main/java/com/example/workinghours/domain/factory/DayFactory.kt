package com.example.workinghours.domain.factory

import com.example.workinghours.domain.model.Day
import org.joda.time.LocalDate
import javax.inject.Inject

class DayFactory @Inject constructor() {
    fun create(date: LocalDate) =
        Day(
            numberOfMonth = date.monthOfYear,
            numberOfDay = date.dayOfMonth,
            numberOfYear = date.year,
            date = date,
        )
}
package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.factory.DayFactory
import com.example.workinghours.domain.model.Day
import org.joda.time.LocalDate
import javax.inject.Inject

class GetDaysOfCurrentMonthUseCase @Inject constructor(
    private val getDaysOfMonth: GetDaysOfMonthUseCase,
) {

    operator fun invoke(): List<Day> {
        val now = LocalDate.now()
        return getDaysOfMonth(now.monthOfYear, now.year)
    }
}
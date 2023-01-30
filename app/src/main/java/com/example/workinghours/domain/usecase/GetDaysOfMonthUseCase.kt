package com.example.workinghours.domain.usecase

import com.example.Utils
import com.example.workinghours.domain.factory.DayFactory
import com.example.workinghours.domain.model.Day
import org.joda.time.LocalDate
import javax.inject.Inject

class GetDaysOfMonthUseCase @Inject constructor(private val dayFactory: DayFactory) {
    operator fun invoke(
        month: Int,
        year: Int,
    ): List<Day> {
        val setMonth = LocalDate(year, month, Utils.DAY_FOR_GET_MONTH)
        val numberOfDays = setMonth.dayOfMonth().maximumValue
        val numberOfMonth = setMonth.monthOfYear
        val numberOfYear = setMonth.year
        val days = mutableListOf<Day>()
        repeat(numberOfDays) { numberOfDay ->
            days.add(
                dayFactory.create(
                    LocalDate()
                        .withDayOfMonth(numberOfDay.plus(1))
                        .withMonthOfYear(numberOfMonth)
                        .withYearOfEra(numberOfYear),
                )
            )
        }
        return days
    }
}
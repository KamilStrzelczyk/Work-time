package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.Day
import org.joda.time.LocalDate
import javax.inject.Inject

class GetDaysOfCurrentMonthUseCase @Inject constructor() {

    operator fun invoke(): List<Day> {
        val now = LocalDate.now()
        val numberOfDays = now.dayOfMonth().maximumValue
        val numberOfMonth = now.monthOfYear
        val numberOfYear = now.year
        val days = mutableListOf<Day>()
        repeat(numberOfDays) { numberOfDay ->
            days.add(
                Day(
                    numberOfDay = numberOfDay.plus(1),
                    numberOfMonth = numberOfMonth,
                    numberOfYear = numberOfYear,
                    date = LocalDate()
                        .withDayOfMonth(numberOfDay.plus(1))
                        .withMonthOfYear(numberOfMonth)
                        .withYearOfEra(numberOfYear),
                )
            )
        }
        return days
    }
}
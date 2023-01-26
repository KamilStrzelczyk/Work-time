package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.DaysOfMonth
import org.joda.time.DateTime
import org.joda.time.LocalDate
import javax.inject.Inject
import kotlin.collections.List

class GetDayOfMonthUseCase @Inject constructor() {
    operator fun invoke(
        currentDate: LocalDate,
    ): List<DaysOfMonth> {
//        val currentDate = DateTime.now()
        val numberOfDays = currentDate.dayOfMonth().maximumValue
        val numberOfMonth = currentDate.monthOfYear
        val numberOfYear = currentDate.year
        val days = mutableListOf<DaysOfMonth>()
        repeat(numberOfDays) { numberOfDay ->
            days.add(
                DaysOfMonth(
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
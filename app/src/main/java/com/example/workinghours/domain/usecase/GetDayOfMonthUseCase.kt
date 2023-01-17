package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.DaysOfMonth
import org.joda.time.DateTime
import javax.inject.Inject
import kotlin.collections.List

class GetDayOfMonthUseCase @Inject constructor() {
    operator fun invoke(): List<DaysOfMonth> {
        val currentDate = DateTime.now()
        val numberOfDays = currentDate.dayOfMonth().maximumValue
        val numberOfMonth = currentDate.monthOfYear
        val numberOfYear = currentDate.year
        val days = mutableListOf<DaysOfMonth>()
        repeat(numberOfDays) { numberOfDay ->
            days.add(DaysOfMonth(
                numberOfDay = numberOfDay.plus(1),
                numberOfMonth = numberOfMonth,
                numberOfYear = numberOfYear,
                date = DateTime().withDayOfMonth(numberOfDay.plus(1))
                    .withMonthOfYear(numberOfMonth)
                    .withYearOfEra(numberOfYear)
            ))
        }
        return days
    }
}

//class GetDayOfMonthUseCase @Inject constructor() {
//    suspend operator fun invoke(): List<Int> {
//        val x = DateTime.now().dayOfMonth().maximumValue
//        val daysOfMonth = mutableListOf<Int>()
//        for (i in 0..x) {
//            daysOfMonth.add(i)
//        }
//        val das = DateTime.now()
//        val nameOfDay = das.withDayOfMonth(24)
//        println(nameOfDay.dayOfWeek().asText)
//        println(das.toLocalDate())
//        val asdewqq = LocalDate.parse("2018-01-23")
//        println(asdewqq.dayOfMonth().maximumValue)
//        return daysOfMonth
//    }
//}
//println(nameOfDay.monthOfYear().getAsText(Locale.ENGLISH))
//val das = DateTime.now()
//val nameOfDay = das.withDayOfMonth(24)
//println(nameOfDay.dayOfWeek().asText)
//println(das.toLocalDate())
//val asdewqq = LocalDate.parse("2018-01-23")
//println(asdewqq.dayOfMonth().maximumValue)
//return daysOfMonth
package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.CalculateAmountOfHours
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class CalculateAmountOfHoursUseCase @Inject constructor() {
    operator fun invoke(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        hygieneHour: Int,
        hygieneMinute: Int,
    ): CalculateAmountOfHours {
        val localTime = LocalDate.now()
        val dateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
        val startWorkTime = dateTimeFormatter.parseDateTime("$startHour:$startMinute")
        val endWorkTime = dateTimeFormatter.parseDateTime("$endHour:$endMinute")
        val hygieneWorkTime = dateTimeFormatter.parseDateTime("$hygieneHour:$hygieneMinute")
        val durationWorkTime = endWorkTime.minusHours(startHour).minusMinutes(startMinute)
        val durationWorkTimeWithHygiene =
            durationWorkTime.minusHours(hygieneHour).minusMinutes(hygieneMinute)
        val convertToDateTime = DateTime(durationWorkTimeWithHygiene)

        return CalculateAmountOfHours(
            userWorkDate = localTime,
            startWorkTime = startWorkTime,
            endWorkTime = endWorkTime,
            hygieneWorkTime = hygieneWorkTime,
            amountWorkTime = convertToDateTime,
        )
    }
}
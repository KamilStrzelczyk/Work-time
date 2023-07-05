package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.CalculateAmountOfHours
import com.example.workinghours.domain.provider.DateProvider
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class CalculateAmountOfHoursUseCase @Inject constructor(
    private val dateProvider: DateProvider,
) {
    operator fun invoke(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        hygieneHour: Int,
        hygieneMinute: Int,
    ): CalculateAmountOfHours {
        val localTime = dateProvider.getLocalDateNow()
        val dateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
        val startWorkTime = dateTimeFormatter.parseLocalTime("$startHour:$startMinute")
        val endWorkTime = dateTimeFormatter.parseLocalTime("$endHour:$endMinute")
        val hygieneWorkTime = dateTimeFormatter.parseLocalTime("$hygieneHour:$hygieneMinute")
        val durationWorkTime = endWorkTime.minusHours(startHour).minusMinutes(startMinute)
        val durationWorkTimeWithHygiene =
            durationWorkTime.minusHours(hygieneHour).minusMinutes(hygieneMinute)
        val convertToDateTime = LocalTime(durationWorkTimeWithHygiene)

        return CalculateAmountOfHours(
            userWorkDate = localTime,
            startWorkTime = startWorkTime,
            endWorkTime = endWorkTime,
            hygieneWorkTime = hygieneWorkTime,
            amountWorkTime = convertToDateTime,
        )
    }
}
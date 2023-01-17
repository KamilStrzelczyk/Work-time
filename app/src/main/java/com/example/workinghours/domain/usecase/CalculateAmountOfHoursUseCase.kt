package com.example.workinghours.domain.usecase


import com.example.workinghours.domain.model.CalculateAmountOfHours
import org.joda.time.DateTime
import org.joda.time.Interval
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
        val localTime = DateTime.now()
        val time = DateTimeFormat.forPattern("HH:mm")
        val startWorkTime = time.parseDateTime("$startHour:$startMinute")
        val endWorkTime = time.parseDateTime("$endHour:$endMinute")
        val hygieneWorkTime = time.parseDateTime("$hygieneHour:$hygieneMinute")
        val duration = Interval(startWorkTime, endWorkTime).toDuration()
        val sum = duration.millis - hygieneWorkTime.millis
        val convertSum = DateTime(sum)
//        return localTime
//            .withHourOfDay(convertSum.hourOfDay)
//            .withMinuteOfHour(convertSum.minuteOfHour)
        return CalculateAmountOfHours(
            userWorkData = localTime,
            startWorkTime = startWorkTime,
            endWorkTime = endWorkTime,
            hygieneWorkTime = hygieneWorkTime,
            amountWorkTime = convertSum
        )
    }
}
package com.example.workinghours.domain.usecase


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
    ): DateTime {
        val localTime = DateTime.now()
        val time = DateTimeFormat.forPattern("HH:mm")
        val startTime = time.parseDateTime("$startHour:$startMinute")
        val endTime = time.parseDateTime("$endHour:$endMinute")
        val secondTime = time.parseDateTime("$hygieneHour:$hygieneMinute")
        val duration = Interval(startTime, endTime).toDuration()
        val sum = duration.millis - secondTime.millis
        val convertSum = DateTime(sum)
        return localTime
            .withHourOfDay(convertSum.hourOfDay)
            .withMinuteOfHour(convertSum.minuteOfHour)
    }
}
package com.example.workinghours.domain.usecase.AddWorkTimeScreenUseCase


import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class CalculateAmountOfHours @Inject constructor() {
    operator fun invoke(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        secondHour: Int,
        secondMinute: Int,
    ): DateTime {

        val time = DateTimeFormat.forPattern("HH:mm")
        val startTime = time.parseDateTime("$startHour:$startMinute")
        val endTime = time.parseDateTime("$endHour:$endMinute")
        val secondTime = time.parseDateTime("$secondHour:$secondMinute")
        val duration = Interval(startTime, endTime).toDuration()
        val sum = duration.millis - secondTime.millis
        val convertSum = DateTime(sum)
        return time.parseDateTime("${convertSum.hourOfDay}:${convertSum.minuteOfHour}")

    }
}
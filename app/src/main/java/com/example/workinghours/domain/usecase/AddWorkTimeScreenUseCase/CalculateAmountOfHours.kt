package com.example.workinghours.domain.usecase.AddWorkTimeScreenUseCase

import androidx.arch.core.executor.DefaultTaskExecutor
import okhttp3.internal.format
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Interval
import org.joda.time.LocalDateTime
import org.joda.time.Period
import org.joda.time.format.DateTimeFormat
import java.sql.Time
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

class CalculateAmountOfHours @Inject constructor() {
    operator fun invoke(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        secondHour: Int,
        secondMinute: Int,
    ): Period {

        var time = DateTimeFormat.forPattern("HH:mm")

        var startTime = time.parseDateTime("$startHour:$startMinute")

        var endTime = time.parseDateTime("$endHour:$endMinute")

        var secondTime = time.parseDateTime("$secondHour:$secondMinute")

        var duration = Interval(startTime, endTime).toDuration()

        var period = duration.toPeriod()

       // var x = period.minusHours(secondHour).minusMinutes(secondMinute)
        var x = period.minusHours(secondTime.hourOfDay)
        var y = x.minusMinutes(secondTime.minuteOfHour)
        duration.millis - secondTime.millis


//        val workTime = (period.hours+period.minutes)
        val workTime = y.toPeriod()
//        val startTime: DateTime = DateTime().withHourOfDay(startHour).withMinuteOfHour(startMinute)
//        val endTime: DateTime = DateTime().withHourOfDay(endHour).withMinuteOfHour(endMinute)
//        val secondTime: DateTime = DateTime().withHourOfDay(secondHour).withMinuteOfHour(secondMinute)
//        val workTime2: DateTime = DateTime().withHourOfDay(0).withMinuteOfHour(0)
//         val  workTime : DateTime = endTime.minusHours(startTime.hourOfDay).minusHours(secondTime.hourOfDay)


//        val startTime = Time(startHour, startMinute, 0)
//        val endTime = Time(endHour, endMinute, 0)
//        val workTime = Time(0,0,0)
//        val secondTime = Time(secondHour,secondMinute,0)
//        workTime.hours = endTime.hours - startTime.hours - secondTime.hours
//        workTime.minutes = endTime.minutes - startTime.minutes - secondTime.minutes
        return workTime
    }
}
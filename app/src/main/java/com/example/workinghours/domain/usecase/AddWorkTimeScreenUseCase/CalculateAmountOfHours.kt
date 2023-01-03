package com.example.workinghours.domain.usecase.AddWorkTimeScreenUseCase

import java.sql.Time
import javax.inject.Inject

class CalculateAmountOfHours @Inject constructor() {
    operator fun invoke(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int,
        secondHour: Int ,
        secondMinute: Int ,
    ): Time {
        val startTime = Time(startHour, startMinute, 0)
        val endTime = Time(endHour, endMinute, 0)
        val workTime = Time(0,0,0)
        val secondTime = Time(secondHour,secondMinute,0)
        workTime.hours = endTime.hours - startTime.hours - secondTime.hours
        workTime.minutes = endTime.minutes - startTime.minutes - secondTime.minutes
        return workTime
    }
}
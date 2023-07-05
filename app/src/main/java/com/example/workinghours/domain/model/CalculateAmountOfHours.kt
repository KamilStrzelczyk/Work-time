package com.example.workinghours.domain.model

import org.joda.time.LocalDate
import org.joda.time.LocalTime

data class CalculateAmountOfHours(
    val userWorkDate: LocalDate,
    val startWorkTime: LocalTime,
    val endWorkTime: LocalTime,
    val hygieneWorkTime: LocalTime,
    val amountWorkTime: LocalTime,
)
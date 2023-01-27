package com.example.workinghours.domain.model

import org.joda.time.LocalDate

data class DaysOfMonth(
    val numberOfMonth: Int,
    val numberOfDay: Int,
    val numberOfYear: Int,
    val date: LocalDate,
)
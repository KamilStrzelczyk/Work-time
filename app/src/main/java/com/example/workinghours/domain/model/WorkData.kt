package com.example.workinghours.domain.model


import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

data class WorkData(
    val id: Int = 0,
    val userId: Int,
    val userName: String,
    val userWorkDate: LocalDate,
    val startWorkTime: LocalTime,
    val endWorkTime: LocalTime,
    val hygieneWorkTime: LocalTime,
    val amountWorkTime: LocalTime,
    val year: Int,
    val monthNumber: Int,
)
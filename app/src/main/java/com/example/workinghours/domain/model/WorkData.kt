package com.example.workinghours.domain.model


import org.joda.time.DateTime
import org.joda.time.LocalDate

data class WorkData(
    val id: Int = 0,
    val userId: Int,
    val userName: String,
    val userWorkDate: LocalDate,
    val startWorkTime: DateTime,
    val endWorkTime: DateTime,
    val hygieneWorkTime: DateTime,
    val amountWorkTime: DateTime,
    val year: Int,
    val monthNumber: Int,
)
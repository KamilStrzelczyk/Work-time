package com.example.workinghours.domain.model

import org.joda.time.DateTime

data class DaysOfMonth(
    val numberOfMonth: Int,
    val numberOfDay: Int,
    val numberOfYear: Int,
    val date: DateTime,
)

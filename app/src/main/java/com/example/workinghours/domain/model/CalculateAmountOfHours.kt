package com.example.workinghours.domain.model

import org.joda.time.DateTime
import org.joda.time.LocalDate

data class CalculateAmountOfHours(
    val userWorkDate: LocalDate,
    val startWorkTime: DateTime,
    val endWorkTime: DateTime,
    val hygieneWorkTime: DateTime,
    val amountWorkTime: DateTime,
    )

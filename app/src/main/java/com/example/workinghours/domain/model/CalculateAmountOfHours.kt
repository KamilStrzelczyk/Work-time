package com.example.workinghours.domain.model

import org.joda.time.DateTime

data class CalculateAmountOfHours(
    val userWorkData: DateTime,
    val startWorkTime: DateTime,
    val endWorkTime: DateTime,
    val hygieneWorkTime: DateTime,
    val amountWorkTime: DateTime,
    )
package com.example.workinghours.domain.model


import org.joda.time.DateTime


data class WorkData(
    var id: Int = 0,
    var userId: Int,
    var userWorkDate: DateTime,
    val startWorkTime: DateTime,
    val endWorkTime: DateTime,
    val hygieneWorkTime: DateTime,
    val amountWorkTime: DateTime,
)

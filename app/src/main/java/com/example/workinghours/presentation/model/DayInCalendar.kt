package com.example.workinghours.presentation.model

data class DayInCalendar(
    val workDate: String,
    val workAmount: String,
    val startWorkTime: String,
    val endWorkTime: String,
    val hygieneTime: String,
    val showWorkAmount: Boolean,
    val showOnlyWorkDay: Boolean,
    val showStartWorkTime: Boolean,
    val showHygieneTime: Boolean,
    val showIfIsSunday: Boolean,
    val showIfIsSaturday: Boolean,
)

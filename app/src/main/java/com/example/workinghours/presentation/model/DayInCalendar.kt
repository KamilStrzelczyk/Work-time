package com.example.workinghours.presentation.model

data class DayInCalendar(
    var workDate: String,
    var workAmount: String,
    var startWorkTime: String,
    var endWorkTime: String,
    var hygieneTime: String,
    val showWorkAmount: Boolean,
    val showOnlyWorkDay: Boolean,
    val showStartWorkTime: Boolean,
    val showHygieneTime: Boolean,
    val showIfIsSunday: Boolean,
)

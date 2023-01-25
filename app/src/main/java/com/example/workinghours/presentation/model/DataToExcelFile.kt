package com.example.workinghours.presentation.model

data class DataToExcelFile(
    val workDate: String,
    val userName: String,
    val workAmount: String,
    val startWorkTime: String,
    val endWorkTime: String,
    val hygieneTime: String,
)
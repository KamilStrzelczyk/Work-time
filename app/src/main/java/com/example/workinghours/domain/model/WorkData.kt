package com.example.workinghours.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class WorkData(
    var id: Int,
    var userName: String,
    var userWorkAmount: Int,
    var userWorkData: Int,
)

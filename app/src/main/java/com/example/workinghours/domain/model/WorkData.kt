package com.example.workinghours.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime


data class WorkData(
    var id: Int = 0,
    var userId: Int,
    var userWorkAmount: DateTime,
    var userWorkData: Int,
)

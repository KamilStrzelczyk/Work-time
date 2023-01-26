package com.example.workinghours.infrastructure.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import org.joda.time.LocalDate

@Entity(tableName = "work_data")
data class WorkDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "user_id")
    var userId: Int,
    @ColumnInfo(name = "user_name")
    var userName: String,
    @ColumnInfo(name = "month_number")
    var monthNumber: Int,
    @ColumnInfo(name = "year")
    var year: Int,
    @ColumnInfo(name = "user_work_amount")
    val amountWorkTime: DateTime,
    @ColumnInfo(name = "user_work_date")
    var userWorkData: LocalDate,
    @ColumnInfo(name = "start_work_time")
    val startWorkTime: DateTime,
    @ColumnInfo(name = "end_work_time")
    val endWorkTime: DateTime,
    @ColumnInfo(name = "hygiene_work_time")
    val hygieneWorkTime: DateTime,
)

package com.example.workinghours.infrastructure.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "work_data")
data class WorkDataEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "user_id")
    var userId: Int,
    @ColumnInfo(name = "user_work_amount")
    var userWorkAmount: DateTime,
    @ColumnInfo(name = "user_work_date")
    var userWorkData: Int,
)

package com.example.workinghours.infrastructure.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey (autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "user_name")
    var userName: String,
    @ColumnInfo(name = "user_password")
    var userPassword: String,
    @ColumnInfo(name = "user_work_amount")
    var userWorkAmount: Int,
    @ColumnInfo(name = "user_work_data")
    var userWorkData: Int,
)

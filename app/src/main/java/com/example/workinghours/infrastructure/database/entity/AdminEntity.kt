package com.example.workinghours.infrastructure.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin")
data class AdminEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "password")
    val password: String,
)

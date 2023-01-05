package com.example.workinghours.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.database.entities.UserEntity

@Database(
entities = [UserEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}
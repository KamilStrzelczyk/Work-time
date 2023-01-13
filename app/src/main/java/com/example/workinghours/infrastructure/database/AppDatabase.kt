package com.example.workinghours.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.database.dao.WorkDataDao
import com.example.workinghours.infrastructure.database.entities.UserEntity
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity

@Database(
    entities = [
        UserEntity::class,
        WorkDataEntity::class,
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getWorkDataDao(): WorkDataDao
    suspend fun initializeDataBase() {
        getUserDao().saveNewUser(
            UserEntity(
                id = 1,
                userName = "Dominik",
                userPassword = "1234",
            ),
            UserEntity(
                id = 2,
                userName = "Maciek",
                userPassword = "1234",
            ),
            UserEntity(
                id = 3,
                userName = "Agnieszka",
                userPassword = "1234",
            ),
            UserEntity(
                id = 4,
                userName = "Sylwia",
                userPassword = "1234",
            ),
            UserEntity(
                id = 5,
                userName = "Agata",
                userPassword = "1234",
            ),
            UserEntity(
                id = 6,
                userName = "Paulina",
                userPassword = "1234",
            ),
            UserEntity(
                id = 7,
                userName = "Irena",
                userPassword = "1234",
            ),
        )
    }
}
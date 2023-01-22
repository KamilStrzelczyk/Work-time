package com.example.workinghours.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.workinghours.infrastructure.database.dao.AdminDao
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.database.dao.WorkDataDao
import com.example.workinghours.infrastructure.database.entities.AdminEntity
import com.example.workinghours.infrastructure.database.entities.UserEntity
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity
import org.joda.time.DateTime

@Database(
    entities = [
        UserEntity::class,
        WorkDataEntity::class,
        AdminEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    TypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getWorkDataDao(): WorkDataDao
    abstract fun getAdminDao(): AdminDao
    suspend fun initializeDataBase() {
        getAdminDao().insert(AdminEntity(password = "1234", id = 1))
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
        getWorkDataDao().saveWorkData(
            WorkDataEntity(
                id = 1,
                userId = 2,
                amountWorkTime = DateTime(1674032400000),
                userWorkData = DateTime(1674118800000),
                startWorkTime = DateTime(1674018000000),
                endWorkTime = DateTime(1674054000000),
                hygieneWorkTime = DateTime(1674000000000)
            ),
            WorkDataEntity(
                id = 2,
                userId = 2,
                amountWorkTime = DateTime(1674032400000),
                userWorkData = DateTime(1672650000000),
                startWorkTime = DateTime(1674018000000),
                endWorkTime = DateTime(1674054000000),
                hygieneWorkTime = DateTime(1674000000000)
            ),
            WorkDataEntity(
                id = 3,
                userId = 2,
                amountWorkTime = DateTime(1674032400000),
                userWorkData = DateTime(1674982800000),
                startWorkTime = DateTime(1674018000000),
                endWorkTime = DateTime(1674054000000),
                hygieneWorkTime = DateTime(1674000000000)
            ),
            WorkDataEntity(
                id = 4,
                userId = 2,
                amountWorkTime = DateTime(1674032400000),
                userWorkData = DateTime(1672909200000),
                startWorkTime = DateTime(1674018000000),
                endWorkTime = DateTime(1674054000000),
                hygieneWorkTime = DateTime(1674000000000)
            )
        )
    }
}
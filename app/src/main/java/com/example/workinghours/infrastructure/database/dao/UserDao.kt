package com.example.workinghours.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workinghours.infrastructure.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewUser(vararg user: UserEntity)

    @Delete
    suspend fun deleteUser(item: UserEntity)
}
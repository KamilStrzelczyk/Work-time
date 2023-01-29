package com.example.workinghours.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workinghours.infrastructure.database.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT user_name FROM user WHERE id =:userId")
    suspend fun getUserName(userId: Int): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNewUser(vararg user: UserEntity)

    @Delete
    suspend fun deleteUser(item: UserEntity)
}
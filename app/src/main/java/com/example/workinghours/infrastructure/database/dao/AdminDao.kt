package com.example.workinghours.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workinghours.infrastructure.database.entities.AdminEntity

@Dao
interface AdminDao {
    @Query("SELECT * FROM admin")
    suspend fun getPassword(): List<AdminEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changePassword(password: AdminEntity)
}
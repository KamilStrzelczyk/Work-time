package com.example.workinghours.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workinghours.infrastructure.database.entities.AdminEntity

@Dao
interface AdminDao {
    @Query("SELECT password FROM admin LIMIT 1 ")
    suspend fun getPassword(): String

    @Query("UPDATE admin SET password = :password WHERE id= :id")
    suspend fun changePassword(password: String, id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(adminEntity: AdminEntity)
}
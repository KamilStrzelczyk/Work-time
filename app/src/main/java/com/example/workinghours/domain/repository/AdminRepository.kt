package com.example.workinghours.domain.repository

import com.example.workinghours.infrastructure.database.entities.AdminEntity

interface AdminRepository {
    suspend fun getPassword(): String
    suspend fun changePassword(password: String, id: Int)
    suspend fun insertPassword(password: AdminEntity)
}
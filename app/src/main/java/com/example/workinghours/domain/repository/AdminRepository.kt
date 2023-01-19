package com.example.workinghours.domain.repository

import com.example.workinghours.domain.model.Admin

interface AdminRepository {
    suspend fun getPassword(): List<Admin>
    suspend fun changePassword(password: Admin)
}
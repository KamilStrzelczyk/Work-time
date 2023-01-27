package com.example.workinghours.domain.repository

interface AdminRepository {
    suspend fun getPassword(): String
    suspend fun changePassword(password: String)
}
package com.example.workinghours.domain.repository

import com.example.workinghours.domain.model.User

interface UserRepository {
    suspend fun getAll(): List<User>
    suspend fun save(user: User)
    suspend fun delete(user: User)
}
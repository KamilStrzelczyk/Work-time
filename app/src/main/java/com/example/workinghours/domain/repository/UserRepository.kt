package com.example.workinghours.domain.repository

import com.example.workinghours.domain.model.User

interface UserRepository {
    suspend fun getAll(): List<User>
    suspend fun saveNewUser(user: User)
    suspend fun deleteUser(user: User)
}

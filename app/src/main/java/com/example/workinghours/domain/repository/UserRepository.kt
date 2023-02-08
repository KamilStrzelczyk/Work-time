package com.example.workinghours.domain.repository

import com.example.workinghours.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getAll(): Flow<List<User>>
    suspend fun save(userName: String, userPassword: String)
    suspend fun delete(user: User)
}
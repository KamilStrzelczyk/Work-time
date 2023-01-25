package com.example.workinghours.infrastructure.repository

import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.repository.UserRepository
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.mapper.UserMapper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val mapper: UserMapper,
    private val userDao: UserDao,
) : UserRepository {

    override suspend fun getAll() = userDao.getAllUsers().map {
        mapper.toDomainModel(it)
    }

    override suspend fun saveNewUser(user: User) {
        userDao.saveNewUser(mapper.toEntityModel(user))
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(mapper.toEntityModel(user))
    }
}

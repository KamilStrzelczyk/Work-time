package com.example.workinghours.infrastructure.repository


import com.example.workinghours.domain.repository.AdminRepository
import com.example.workinghours.infrastructure.database.dao.AdminDao
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminDao: AdminDao,
) : AdminRepository {
    override suspend fun getPassword(): String = adminDao.getPassword()

    override suspend fun changePassword(password: String) {
        adminDao.changePassword(password)
    }
}
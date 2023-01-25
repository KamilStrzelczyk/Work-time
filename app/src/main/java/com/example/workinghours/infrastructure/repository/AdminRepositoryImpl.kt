package com.example.workinghours.infrastructure.repository


import com.example.workinghours.domain.repository.AdminRepository
import com.example.workinghours.infrastructure.database.dao.AdminDao
import com.example.workinghours.infrastructure.database.entities.AdminEntity
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminDao: AdminDao,
) : AdminRepository {
    override suspend fun getPassword(): String = adminDao.getPassword()

    override suspend fun changePassword(password: String, id: Int) {
        adminDao.changePassword(password, id)
    }

    override suspend fun insertPassword(password: AdminEntity) {
        adminDao.insert(password)
    }

}
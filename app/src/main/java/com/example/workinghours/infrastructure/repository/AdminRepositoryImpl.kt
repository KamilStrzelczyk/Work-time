package com.example.workinghours.infrastructure.repository


import com.example.workinghours.domain.model.Admin
import com.example.workinghours.domain.repository.AdminRepository
import com.example.workinghours.infrastructure.database.dao.AdminDao
import com.example.workinghours.infrastructure.mapper.AdminMapper
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminDao: AdminDao,
    private val mapper: AdminMapper,
) : AdminRepository {
    override suspend fun getPassword(): List<Admin> = adminDao.getPassword().map {
        mapper.toDomainModel(it)
    }

    override suspend fun changePassword(password: Admin) {
        adminDao.changePassword(mapper.toEntityModel(password))
    }
}
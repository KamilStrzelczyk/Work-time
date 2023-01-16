package com.example.workinghours.infrastructure.repository

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.repository.WorkDataRepository
import com.example.workinghours.infrastructure.database.dao.WorkDataDao
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity
import com.example.workinghours.infrastructure.mapper.WorkDataMapper
import javax.inject.Inject

class WorkDataRepositoryImpl @Inject constructor(
    private val workDataDao: WorkDataDao,
    private val mapper: WorkDataMapper,

    ) : WorkDataRepository {
    override suspend fun saveNewData(workData: WorkData) {
        workDataDao.saveWorkData(mapper.toEntityModel(workData))
    }

    override suspend fun getAllDate(id: Int): List<WorkData> {
        val list: List<WorkDataEntity> = workDataDao.getAllDate(id)
        val domainList = list.map { mapper.toDomainModel(it) }
        return domainList
    }

}
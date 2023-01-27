package com.example.workinghours.infrastructure.repository

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.repository.WorkDataRepository
import com.example.workinghours.infrastructure.database.dao.WorkDataDao
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity
import com.example.workinghours.infrastructure.mapper.WorkDataMapper
import org.joda.time.LocalDate
import javax.inject.Inject

class WorkDataRepositoryImpl @Inject constructor(
    private val workDataDao: WorkDataDao,
    private val mapper: WorkDataMapper,

    ) : WorkDataRepository {
    override suspend fun save(workData: WorkData) {
        workDataDao.saveWorkData(mapper.toEntityModel(workData))
    }

    override suspend fun getAllData(userId: Int): List<WorkData> {
        val list: List<WorkDataEntity> = workDataDao.getAllDate(userId)
        return list.map { mapper.toDomainModel(it) }
    }

    override suspend fun getDataFromOneDay(userWorkDate: LocalDate): List<WorkData> {
        val list: List<WorkDataEntity> = workDataDao.getDateFromOneDay(userWorkDate)
        return list.map { mapper.toDomainModel(it) }
    }

    override suspend fun getDataFromOneMonth(year: Int, month: Int): List<WorkData> {
        val list: List<WorkDataEntity> = workDataDao.getDateFromOneMonth(year, month)
        return list.map { mapper.toDomainModel(it) }
    }
}
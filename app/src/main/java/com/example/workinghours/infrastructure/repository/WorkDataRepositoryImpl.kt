package com.example.workinghours.infrastructure.repository

import com.example.workinghours.domain.factory.DayFactory
import com.example.workinghours.domain.model.Day
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.repository.WorkDataRepository
import com.example.workinghours.domain.usecase.GetDaysOfMonthUseCase
import com.example.workinghours.infrastructure.database.dao.UserDao
import com.example.workinghours.infrastructure.database.dao.WorkDataDao
import com.example.workinghours.infrastructure.database.entity.WorkDataEntity
import com.example.workinghours.infrastructure.mapper.WorkDataMapper
import com.example.workinghours.infrastructure.utils.ReportGenerator
import com.example.workinghours.infrastructure.utils.ReportGenerator.Companion.HOUR_FORMAT
import com.example.workinghours.infrastructure.utils.ReportGenerator.ReportWorkData
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.io.File
import javax.inject.Inject

class WorkDataRepositoryImpl @Inject constructor(
    private val workDataDao: WorkDataDao,
    private val useDao: UserDao,
    private val mapper: WorkDataMapper,
    private val reportGenerator: ReportGenerator,
    private val getDaysOfMonth: GetDaysOfMonthUseCase,
    private val dayFactory: DayFactory,
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

    override suspend fun generateMonthReport(year: Int, month: Int): File? {
        val reportWorkData = workDataDao.getDateFromOneMonth(year, month)
            .groupBy { it.userId }
            .map { mapUserIdToName(it.key) to mapWorkDataToReportData(it.value) }
            .toMap()

        return reportGenerator.generateReportForMonth(
            daysOfCurrentMonth = getDaysOfMonth(
                month = month,
                year = year,
            ),
            data = reportWorkData,
            sheetName = LocalDate(year, month, 1).monthOfYear().asText
        )
    }

    override suspend fun generateDailyReport(date: LocalDate): File? {
        val reportWorkData = workDataDao.getDateFromOneDay(date)
            .groupBy { it.userId }
            .map { mapUserIdToName(it.key) to mapWorkDataToReportData(it.value) }
            .toMap()

        return reportGenerator.generateReportForDay(
            day = dayFactory.create(date),
            data = reportWorkData,
            sheetName = date.toString()
        )
    }

    private suspend fun mapUserIdToName(userId: Int) = useDao.getUserName(userId)

    private fun mapWorkDataToReportData(value: List<WorkDataEntity>) = value.map {
        ReportWorkData(
            data = it.userWorkDate,
            startTime = it.startWorkTime.toString(HOUR_FORMAT),
            endTime = it.endWorkTime.toString(HOUR_FORMAT),
            hygiene = it.hygieneWorkTime.toString(HOUR_FORMAT),
            totalAmount = it.amountWorkTime.toString(HOUR_FORMAT),
        )
    }
}
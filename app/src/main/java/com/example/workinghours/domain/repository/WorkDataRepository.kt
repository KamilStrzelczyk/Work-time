package com.example.workinghours.domain.repository

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.infrastructure.database.entities.WorkDataEntity
import org.joda.time.DateTime
import org.joda.time.LocalDate

interface WorkDataRepository {
    suspend fun saveNewData(workData: WorkData)
    suspend fun getAllDate(userId: Int): List<WorkData>
    suspend fun getDateFromOneDay(userWorkDate: LocalDate): List<WorkData>
    suspend fun getDateFromOneMonth(year: Int, month: Int): List<WorkData>
}
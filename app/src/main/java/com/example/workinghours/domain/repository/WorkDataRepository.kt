package com.example.workinghours.domain.repository

import com.example.workinghours.domain.model.WorkData
import org.joda.time.LocalDate

interface WorkDataRepository {
    suspend fun save(workData: WorkData)
    suspend fun getAllData(userId: Int): List<WorkData>
    suspend fun getDataFromOneDay(userWorkDate: LocalDate): List<WorkData>
    suspend fun getDataFromOneMonth(year: Int, month: Int): List<WorkData>
}
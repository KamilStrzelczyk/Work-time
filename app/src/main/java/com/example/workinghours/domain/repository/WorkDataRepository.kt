package com.example.workinghours.domain.repository

import com.example.workinghours.domain.model.WorkData

interface WorkDataRepository {
    suspend fun saveNewData(workData: WorkData)
    suspend fun getAllDate(id: Int): List<WorkData>
}
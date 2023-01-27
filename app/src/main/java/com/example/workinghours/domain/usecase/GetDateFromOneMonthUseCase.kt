package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.repository.WorkDataRepository
import javax.inject.Inject

class GetDateFromOneMonthUseCase @Inject constructor(private val workDataRepository: WorkDataRepository) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
    ): List<WorkData> {
        return workDataRepository.getDataFromOneMonth(year, month)
    }
}
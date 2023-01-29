package com.example.workinghours.domain.usecase

import com.example.workinghours.infrastructure.repository.WorkDataRepositoryImpl
import javax.inject.Inject

class GenerateMonthReportUseCase @Inject constructor(
    private val workDataRepository: WorkDataRepositoryImpl,
) {

    suspend operator fun invoke() = workDataRepository.generateMonthReport()
}
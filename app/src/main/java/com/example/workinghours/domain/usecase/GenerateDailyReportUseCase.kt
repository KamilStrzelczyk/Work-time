package com.example.workinghours.domain.usecase

import com.example.workinghours.infrastructure.repository.WorkDataRepositoryImpl
import org.joda.time.LocalDate
import javax.inject.Inject

class GenerateDailyReportUseCase @Inject constructor(
    private val repository: WorkDataRepositoryImpl,
) {
    suspend operator fun invoke(
        date: LocalDate,
    ) = repository.generateDailyReport(date)
}
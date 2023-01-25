package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.repository.WorkDataRepository
import org.joda.time.LocalDate
import javax.inject.Inject

class GetDateFromOneDayUseCase @Inject constructor(private val workDataRepository: WorkDataRepository) {
    suspend operator fun invoke(currentDate: LocalDate): List<WorkData> {
        return workDataRepository.getDateFromOneDay(LocalDate(currentDate))
    }
}
package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.repository.WorkDataRepository
import javax.inject.Inject

class GetUserDateUseCase @Inject constructor(private val workDataRepository: WorkDataRepository) {
    suspend operator fun invoke(
        userId: Int,
    ) : List<WorkData> {
        return workDataRepository.getAllDate(id = userId)
    }
}
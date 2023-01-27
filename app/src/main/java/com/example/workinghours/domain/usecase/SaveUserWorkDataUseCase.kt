package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.repository.WorkDataRepository
import javax.inject.Inject

class SaveUserWorkDataUseCase @Inject constructor(private val repository: WorkDataRepository) {
    suspend operator fun invoke(workData: WorkData) {
        repository.save(workData)
    }
}
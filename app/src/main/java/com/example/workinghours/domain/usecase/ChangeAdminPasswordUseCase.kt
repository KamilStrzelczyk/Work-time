package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.repository.AdminRepository
import javax.inject.Inject

class ChangeAdminPasswordUseCase @Inject constructor(private val adminRepository: AdminRepository) {
    suspend operator fun invoke(
        password: String,
    ) = adminRepository.changePassword(password = password, id = 1)
}
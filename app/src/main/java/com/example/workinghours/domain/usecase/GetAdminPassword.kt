package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.repository.AdminRepository
import javax.inject.Inject

class GetAdminPassword @Inject constructor(private val adminRepository: AdminRepository) {
    suspend operator fun invoke() =
        adminRepository.getPassword()
}
package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.Admin
import com.example.workinghours.domain.repository.AdminRepository
import javax.inject.Inject

class ChangeAdminPassword @Inject constructor(private val adminRepository: AdminRepository) {
    suspend operator fun invoke(
        password: String,
    ) =
        adminRepository.changePassword(Admin(id = 1, password = password))
}
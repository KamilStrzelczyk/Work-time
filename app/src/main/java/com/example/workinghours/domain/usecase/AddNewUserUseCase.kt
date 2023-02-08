package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.repository.UserRepository
import javax.inject.Inject

class AddNewUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(
        userName: String,
        userPassword: String,
    ) {
        repository.save(
            userName = userName,
            userPassword = userPassword
        )
    }
}
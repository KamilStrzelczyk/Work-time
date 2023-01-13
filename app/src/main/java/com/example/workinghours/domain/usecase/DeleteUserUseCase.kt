package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(
        id: Int,
    ) = repository.deleteUser(User(id = id, "", ""))
}
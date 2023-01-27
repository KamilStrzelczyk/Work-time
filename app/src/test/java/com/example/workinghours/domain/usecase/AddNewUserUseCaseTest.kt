package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.repository.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import javax.inject.Inject

internal class AddNewUserUseCaseTest @Inject constructor(repository: UserRepository) {

    private val useCase = AddNewUserUseCase(repository)
    private val userName = "Jacek"
    private val userPassword = "1234"

    @Test
    suspend fun `WHEN user pass correct data THEN new user should by added`() {
        // GIVEN
        val expectedResult = User(userName = "Jacek", userPassword = "1234")

        // WHEN
        val result = useCase(userName, userPassword)

        // THEN
        assertEquals(expectedResult, result)
    }
}
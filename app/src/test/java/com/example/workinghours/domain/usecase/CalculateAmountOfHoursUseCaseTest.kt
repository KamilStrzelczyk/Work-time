package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.CalculateAmountOfHours
import com.example.workinghours.domain.provider.DateProvider
import io.mockk.every
import io.mockk.mockk
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CalculateAmountOfHoursUseCaseTest {

    private val dateProvider: DateProvider = mockk()
    private val useCase = CalculateAmountOfHoursUseCase(dateProvider)

    private val startHour = 8
    private val startMinute = 30
    private val endHour = 17
    private val endMinute = 45
    private val hygieneHour = 1
    private val hygieneMinute = 20

    @Test
    fun `WHEN user pass correct data THEN result shout be calculated`() {
        // GIVEN
        val expectedResult = CalculateAmountOfHours(
            userWorkDate = LocalDate(1673996400000),
            startWorkTime = LocalTime(1495800000),
            endWorkTime = LocalTime(1529100000),
            hygieneWorkTime = LocalTime(1470000000),
            amountWorkTime = LocalTime(1493700000)
        )
        every { dateProvider.getLocalDateNow() } returns LocalDate(1673996400000)

        // WHEN
        val result = useCase.invoke(
            startHour = startHour,
            startMinute = startMinute,
            endHour = endHour,
            endMinute = endMinute,
            hygieneHour = hygieneHour,
            hygieneMinute = hygieneMinute
        )

        // THEN
        assertEquals(expectedResult, result)
    }
}

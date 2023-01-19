package com.example.workinghours.domain.usecase

import com.example.workinghours.domain.model.CalculateAmountOfHours
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test

internal class CalculateAmountOfHoursUseCaseTest {

    private val useCase = CalculateAmountOfHoursUseCase()

    private val startHour = 8
    private val startMinute = 30
    private val endHour = 17
    private val endMinute = 45
    private val hygieneHour = 1
    private val hygieneMinute = 20
//for hour use 1970 YYYY
    @Test
    fun `WHEN user pass correct data THEN result shout be calculated`() {
        val expectedResult = CalculateAmountOfHours(
            userWorkData = DateTime(1673996400000),
            startWorkTime = DateTime(1495800000),
            endWorkTime = DateTime(1529100000),
            hygieneWorkTime = DateTime(1470000000),
            amountWorkTime = DateTime(1493700000))
        //WHEN
        val result = useCase.invoke(
            startHour = startHour,
            startMinute = startMinute,
            endHour = endHour,
            endMinute = endMinute,
            hygieneHour = hygieneHour,
            hygieneMinute = hygieneMinute)
        //THEN
        assertEquals(expectedResult, result)
    }
}

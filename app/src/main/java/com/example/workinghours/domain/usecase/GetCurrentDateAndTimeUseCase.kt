package com.example.workinghours.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime
import javax.inject.Inject

class GetCurrentDateAndTimeUseCase @Inject constructor() {
    suspend operator fun invoke(): Flow<DateTime> =
        flow {
            while (true) {
                val dataTime = DateTime.now()
                emit(dataTime)
                delay(30000)
            }
        }
}
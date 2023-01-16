package com.example.workinghours.domain.usecase

import android.provider.ContactsContract.Data
import org.joda.time.DateTime
import javax.inject.Inject

class GetDayOfMonthUseCase @Inject constructor() {
    suspend operator fun invoke(): List<Int> {
        val x = DateTime.now().dayOfMonth().maximumValue
        val daysOfMonth = mutableListOf<Int>()
        for (i in 0..x) {
            daysOfMonth.add(i)
        }
        return daysOfMonth
    }
}

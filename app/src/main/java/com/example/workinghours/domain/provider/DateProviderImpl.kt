package com.example.workinghours.domain.provider

import org.joda.time.LocalDate
import javax.inject.Inject

class DateProviderImpl @Inject constructor() : DateProvider {
    override fun getLocalDateNow(): LocalDate = LocalDate.now()

}
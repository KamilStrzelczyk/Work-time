package com.example.workinghours.domain.provider

import org.joda.time.LocalDate

interface DateProvider {

    fun getLocalDateNow(): LocalDate
}
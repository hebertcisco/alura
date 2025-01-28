package org.example.alugames.model

import java.time.LocalDate
import java.time.Period

data class Period(
    val initialDate: LocalDate,
    val finalDate: LocalDate) {
    val inDays = Period.between(initialDate, finalDate).days
}

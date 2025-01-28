package org.example.alugames.util

import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

fun String.transformInAge(): Int {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    var age = 0
    val birthdate = LocalDate.parse(this, formatter)
    val today = LocalDate.now()
    age = Period.between(birthdate, today).years

    return age
}
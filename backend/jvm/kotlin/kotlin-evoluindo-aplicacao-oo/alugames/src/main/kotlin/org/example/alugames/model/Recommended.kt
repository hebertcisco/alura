package org.example.alugames.model

interface Recommended {
    val average: Double

    fun recommend(nota: Int)
}
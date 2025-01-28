package org.example.alugames.model

sealed class Plan(val type: String) {

    open fun getValue(rent: Rent): Double {
        return rent.game.price * rent.period.inDays
    }
}
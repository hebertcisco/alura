package org.example.alugames.model

data class Rent(
    val gamer: Gamer,
    val game: Game,
    val period: Period) {
    private val rentalValue = gamer.plan.getValue(this)

    override fun toString(): String {
        return "Rental of the game ${game.title} by ${gamer.name} for the price R\$$rentalValue"
    }
}

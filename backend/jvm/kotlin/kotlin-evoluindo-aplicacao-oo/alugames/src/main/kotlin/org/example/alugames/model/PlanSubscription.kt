package org.example.alugames.model

class PlanSubscription(
    type: String,
    val monthlyFee: Double,
    private val gamesIncluded: Int,
    private val percentageDiscountReputation: Double): Plan(type) {

    override fun getValue(rent: Rent): Double {
        val totalGamesInMonth = rent.gamer.gamesOfTheMonth(rent.period.initialDate.monthValue).size+1

        return if (totalGamesInMonth <= gamesIncluded) {
            0.0
        } else {
            var originalValue = super.getValue(rent)
            if (rent.gamer.average > 8) {
                originalValue -= originalValue * percentageDiscountReputation
            }
            originalValue
        }

    }
}

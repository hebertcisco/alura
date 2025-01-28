package org.example.alugames.model

 class SinglePlan(
     type: String): Plan(type) {

     override fun getValue(rent: Rent): Double {
         var originalValue = super.getValue(rent)
         if (rent.gamer.average > 8) {
             originalValue -= originalValue * 0.1
         }
         return originalValue
     }
}

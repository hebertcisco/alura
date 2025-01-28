package org.example.alugames.model

import com.google.gson.annotations.Expose

data class Game(@Expose val title:String,
                @Expose val thumb:String): Recommended {
    var description: String? = null
    var price = 0.0
    private val listNotes = mutableListOf<Int>()
    override val average: Double
        get() = listNotes.average()

    override fun recommend(nota: Int) {
        listNotes.add(nota)
    }

    constructor(title: String, thumb: String, price: Double, description: String):
            this(title, thumb) {
        this.price = price
        this.description = description
    }
    override fun toString(): String {
        return "My Game: \n" +
                "Title: $title \n" +
                "Cover: $thumb \n" +
                "Description: $description \n" +
                "Price: $price \n" +
                "Reputation: $average"
    }
}
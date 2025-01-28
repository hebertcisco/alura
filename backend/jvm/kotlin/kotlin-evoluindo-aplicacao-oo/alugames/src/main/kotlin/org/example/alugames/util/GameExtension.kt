package org.example.alugames.util

import org.example.alugames.model.InfoGameJson
import org.example.alugames.model.Game

fun InfoGameJson.createGame(): Game {
    return Game(this.title, this.thumb, this.price, this.description)
}
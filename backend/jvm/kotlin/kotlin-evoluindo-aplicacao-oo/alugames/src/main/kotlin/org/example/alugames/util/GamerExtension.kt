package org.example.alugames.util

import org.example.alugames.model.Gamer
import org.example.alugames.model.InfoGamerJson

fun InfoGamerJson.createGamer(): Gamer {
    return Gamer(this.name, this.email, this.birthDay, this.username)
}
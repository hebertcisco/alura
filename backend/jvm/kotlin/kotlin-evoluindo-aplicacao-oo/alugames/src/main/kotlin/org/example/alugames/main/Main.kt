package org.example.alugames.main

import org.example.alugames.model.Gamer
import org.example.alugames.model.Game
import org.example.alugames.services.ApiService
import org.example.alugames.util.transformInAge
import java.util.Scanner


fun main() {
    val read = Scanner(System.`in`)
    val gamer = Gamer.createGamer(read)
    println("Registration completed successfully. Gamer data:")
    println(gamer)
    println("Gamer age:" + gamer.birthday?.transformInAge())

    do {
        println("Enter a game code to search:")
        val search = read.nextLine()

        val searchApi = ApiService()
        val gameInfo = searchApi.searchGame(search)


        var myGame: Game? = null

        val result = runCatching {
            myGame = Game(
                gameInfo.info.title,
                gameInfo.info.thumb
            )
        }

        result.onFailure {
            println("Game does not exist. Try another id.")
        }

        result.onSuccess {
            println("Do you want to enter a custom description? Y/N")
            val option = read.nextLine()
            if (option.equals("s", true)) {
                println("Enter custom description for the game:")
                val customDescription = read.nextLine()
                myGame?.description = customDescription
            } else {
                myGame?.description = myGame?.title
            }

            gamer.gamesSearched.add(myGame)
        }

        println("Do you want to search for a new game? Y/N")
        val response = read.nextLine()

    } while (response.equals("s", true))

    println("Searched games:")
    println(gamer.gamesSearched)

    println("\n Games sorted by title: ")
    gamer.gamesSearched.sortBy {
        it?.title
    }

    gamer.gamesSearched.forEach {
        println("Title: " + it?.title)
    }

    val gamesFiltered = gamer.gamesSearched.filter {
        it?.title?.contains("batman", true) ?: false
    }
    println("\nFiltered games: ")
    println(gamesFiltered)

    println("Do you want to delete a game from the original list? Y/N")
    val option = read.nextLine()
    if (option.equals("s", true)) {
        println(gamer.gamesSearched)
        println("\nEnter the position of the game you want to delete: ")
        val position = read.nextInt()
        gamer.gamesSearched.removeAt(position)
    }

    println("\nUpdated list:")
    println(gamer.gamesSearched)

    println("Search completed successfully.")

}
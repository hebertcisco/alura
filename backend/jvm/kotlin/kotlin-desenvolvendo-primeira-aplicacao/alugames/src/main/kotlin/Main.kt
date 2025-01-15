package org.example
import org.example.model.Game
import org.example.model.Gamer
import org.example.services.GameApiService
import org.example.utils.transformToAge
import java.util.Scanner

fun main() {
    val input = Scanner(System.`in`)
    val gamer = Gamer.createGamer(input)
    println("Registration completed successfully. Gamer data:")
    println(gamer)
    println("Gamer's age: " + gamer.birthDate?.transformToAge())

    do {
        println("Enter a game code to search:")
        val search = input.nextLine()

        val gameApiService = GameApiService()
        val gameInfo = gameApiService.searchGame(search)

        var myGame: Game? = null

        val result = runCatching {
            myGame = Game(
                gameInfo.info.title,
                gameInfo.info.thumb
            )
        }

        result.onFailure {
            println("Nonexistent game. Try another id.")
        }

        result.onSuccess {
            println("Do you want to enter a custom description? Y/N")
            val option = input.nextLine()
            if (option.equals("y", true)) {
                println("Enter the custom description for the game:")
                val customDescription = input.nextLine()
                myGame?.description = customDescription
            } else {
                myGame?.description = myGame?.title.toString()
            }

            myGame?.let { game -> gamer.searchedGames.add(game) }
        }

        println("Do you want to search for a new game? Y/N")
        val response = input.nextLine()

    } while (response.equals("y", true))

    println("Searched games:")
    println(gamer.searchedGames)

    println("\n Games sorted by title: ")
    gamer.searchedGames.sortBy {
        it?.title
    }

    gamer.searchedGames.forEach {
        println("Title: " + it?.title)
    }

    val filteredGames = gamer.searchedGames.filter {
        it?.title?.contains("batman", true) ?: false
    }
    println("\n Filtered games: ")
    println(filteredGames)

    println("Do you want to delete any game from the original list? Y/N")
    val option = input.nextLine()
    if (option.equals("y", true)) {
        println(gamer.searchedGames)
        println("\nEnter the position of the game you want to delete: ")
        val position = input.nextInt()
        gamer.searchedGames.removeAt(position)
    }

    println("\n Updated list:")
    println(gamer.searchedGames)

    println("Search completed successfully.")
}
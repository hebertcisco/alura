package org.example.alugames.model

import java.util.Scanner
import kotlin.random.Random

data class Gamer(var name:String, var email:String): Recommended {
    var birthday:String? = null
    private var user:String? = null
        set(value) {
            field = value
            if(idInternal.isNullOrBlank()) {
                createInternalId()
            }
        }
    private var idInternal:String? = null
    var plan: Plan = SinglePlan("Basic")
    val gamesSearched = mutableListOf<Game?>()
    private val gamesRent = mutableListOf<Rent>()
    private val listNotes = mutableListOf<Int>()
    private val recommendedGames = mutableListOf<Game>()

    override val average: Double
        get() = listNotes.average()

    override fun recommend(nota: Int) {
        listNotes.add(nota)
    }

    fun recommendGame(game: Game, recommendNote: Int) {
        game.recommend(recommendNote)
        recommendedGames.add(game)
    }

    constructor(nome: String, email: String, birthday:String, user:String):
            this(nome, email) {
        this.birthday = birthday
        this.user = user
        createInternalId()
    }

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("Name cannot be blank")
        }
        this.email = validateEmail()
    }

    override fun toString(): String {
        return "Gamer:\n" +
                "Name: $name\n" +
                "Email: $email\n" +
                "Birthdate: $birthday\n" +
                "User: $user\n" +
                "InternalId: $idInternal\n" +
                "Reputation: $average"
    }

    private fun createInternalId() {
        val number = Random.nextInt(10000)
        val tag = String.format("%04d", number)

        idInternal = "$user#$tag"
    }

    private fun validateEmail(): String {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        if (regex.matches(email)) {
            return email
        } else {
            throw IllegalArgumentException("Invalid email")
        }
    }

    fun rentGame(game: Game, period: Period): Rent {
        val rent = Rent(this, game, period)
        gamesRent.add(rent)

        return rent
    }

    fun gamesOfTheMonth(month:Int): List<Game> {
        return gamesRent
            .filter { rent ->  rent.period.initialDate.monthValue == month}
            .map { rent ->  rent.game}
    }

    companion object {
        fun createGamer(read: Scanner): Gamer {
            println("Welcome to AluGames! Let's register. Enter your name:")
            val name = read.nextLine()
            println("Enter your e-mail:")
            val email = read.nextLine()
            println("Do you want to complete your registration with username and date of birth? (Y/N)")
            val option = read.nextLine()

            if (option.equals("y", true)) {
                println("Enter your date of birth (DD/MM/YYYY):")
                val birth = read.nextLine()
                println("Enter your username:")
                val user = read.nextLine()

                return Gamer(name, email, birth, user)
            } else {
                return Gamer (name, email)
            }

        }
    }

}

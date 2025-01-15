package org.example.model

import java.util.Scanner
import kotlin.random.Random

data class Gamer(var name: String, var email:String){
    var birthDate: String? = null
    private var user: String? = null
        set(value) {
            field = value?.toLowerCase()
            if (internalId.isNullOrBlank()) {
                createInternalId()
            }
        }
    private var internalId: String? = null
    val searchedGames = mutableListOf<Game>()

    constructor(name: String, email: String, birthDate: String, user: String) : this(name, email) {
        this.birthDate = birthDate
        this.user = user
        createInternalId()
    }

    init {
        if (name.isBlank()){
            throw IllegalArgumentException("Name can't be null or empty")
        }
        this.email = validatedEmail()
    }
    override fun toString(): String {
        return "Gamer(name='$name', email='$email', birthDate=$birthDate, user=$user, internalId=$internalId)"
    }
    private fun createInternalId() {
        val randomNumber = Random.nextInt(10000)
        val tag = String.format("%04d", randomNumber)

        internalId = "$user#$tag"
    }

    private fun validatedEmail(): String {
        val regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        if (regex.matches(email)) {
            return email
        } else {
            throw IllegalArgumentException("Invalid email")
        }
    }
    companion object {
        fun createGamer(scanner: Scanner): Gamer {
            println("Welcome to AluGames! Let's register you. Enter your name:")
            val name = scanner.nextLine()
            println("Enter your email:")
            val email = scanner.nextLine()
            println("Do you want to complete your registration with a username and birth date? (Y/N)")
            val option = scanner.nextLine()

            return if (option.equals("y", true)) {
                println("Enter your birth date(DD/MM/YYYY):")
                val birthDate = scanner.nextLine()
                println("Enter your username:")
                val user = scanner.nextLine()

                Gamer(name, email, birthDate, user)
            } else {
                Gamer(name, email)
            }
        }
    }
}

package org.example.alugames.services

import org.example.alugames.model.Gamer
import org.example.alugames.model.InfoGamerJson
import org.example.alugames.model.InfoGame
import org.example.alugames.model.InfoGameJson
import org.example.alugames.model.Game
import org.example.alugames.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class ApiService {
    private fun getData(url: String): String {
        val client: HttpClient = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()
        val response = client
            .send(request, HttpResponse.BodyHandlers.ofString())

        return response.body()
    }

    fun searchGame(id:String): InfoGame {
        val url = "https://www.cheapshark.com/api/1.0/games?id=$id"
        val json = getData(url)

        val gson = Gson()
        val gameResponse = gson.fromJson(json, InfoGame::class.java)

        return gameResponse

    }

    fun searchGamers(): List<Gamer> {
        val url = "https://raw.githubusercontent.com/jeniblodev/arquivosJson/main/gamers.json"
        val json = getData(url)

        val gson = Gson()
        val gamerType = object : TypeToken<List<InfoGamerJson>>() {}.type
        val gamerList:List<InfoGamerJson> = gson.fromJson(json, gamerType)

        val convertedGamerList = gamerList.map { infoGamerJson -> infoGamerJson.createGamer() }

        return convertedGamerList
    }

    fun searchGamesJson(): List<Game> {
        val url = "https://raw.githubusercontent.com/jeniblodev/arquivosJson/main/jogos.json"
        val json = getData(url)

        val gson = Gson()
        val gameType = object : TypeToken<List<InfoGameJson>>() {}.type
        val gameList: List<InfoGameJson> = gson.fromJson(json, gameType)

        val convertedGameList = gameList.map { infoGameJson -> infoGameJson.createGame() }

        return convertedGameList
    }

}
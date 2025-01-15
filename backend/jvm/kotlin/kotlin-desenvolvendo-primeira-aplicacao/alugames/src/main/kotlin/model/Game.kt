package org.example.model

import com.google.gson.annotations.SerializedName

class Game(
    @SerializedName("info.title")
    val title: String = "",

    @SerializedName("info.thumb")
    val coverImage: String = "",

    @SerializedName("info.description")
    var description: String = ""
) {
    override fun toString(): String {
        return "Game(title='$title', coverImage='$coverImage', description='$description')"
    }
}
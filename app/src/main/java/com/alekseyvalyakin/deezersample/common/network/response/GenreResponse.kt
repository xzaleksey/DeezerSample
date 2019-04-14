package com.alekseyvalyakin.deezersample.common.network.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//TODO change to model
data class GenreResponse(
    @SerializedName("id")
    val id: Int, // 113
    @SerializedName("name")
    val name: String, // Танцевальная музыка
    @SerializedName("picture")
    val picture: String, // https://api.deezer.com/genre/113/image
    @SerializedName("type")
    val type: String // genre
) : Serializable
package com.alekseyvalyakin.deezersample.common.network.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//TODO change to extend model
data class Contributor(
    @SerializedName("id")
    val id: Int, // 27
    @SerializedName("link")
    val link: String, // https://www.deezer.com/artist/27
    @SerializedName("name")
    val name: String, // Daft Punk
    @SerializedName("picture")
    val picture: String, // https://api.deezer.com/artist/27/image
    @SerializedName("picture_big")
    val pictureBig: String, // https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/500x500-000000-80-0-0.jpg
    @SerializedName("picture_medium")
    val pictureMedium: String, // https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/250x250-000000-80-0-0.jpg
    @SerializedName("picture_small")
    val pictureSmall: String, // https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/56x56-000000-80-0-0.jpg
    @SerializedName("picture_xl")
    val pictureXl: String, // https://cdns-images.dzcdn.net/images/artist/f2bc007e9133c946ac3c3907ddc5d2ea/1000x1000-000000-80-0-0.jpg
    @SerializedName("radio")
    val radio: Boolean, // true
    @SerializedName("role")
    val role: String, // Main
    @SerializedName("share")
    val share: String, // https://www.deezer.com/artist/27?utm_source=deezer&utm_content=artist-27&utm_term=0_1555163830&utm_medium=web
    @SerializedName("tracklist")
    val tracklist: String, // https://api.deezer.com/artist/27/top?limit=50
    @SerializedName("type")
    val type: String // artist
) : Serializable
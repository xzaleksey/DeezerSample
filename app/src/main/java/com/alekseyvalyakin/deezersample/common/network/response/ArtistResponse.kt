package com.alekseyvalyakin.deezersample.common.network.response

import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.google.gson.annotations.SerializedName


data class ArtistResponse(
    @SerializedName("id")
    override val id: String, // 13

    @SerializedName("link")
    override val link: String, // https://www.deezer.com/artist/13

    @SerializedName("name")
    override val name: String, // Eminem

    @SerializedName("nb_album")
    override val nbAlbum: Int, // 58

    @SerializedName("nb_fan")
    override val nbFan: Int, // 11913242

    @SerializedName("picture")
    override val picture: String, // https://api.deezer.com/artist/13/image

    @SerializedName("picture_big")
    override val pictureBig: String, // https://e-cdns-images.dzcdn.net/images/artist/0707267475580b1b82f4da20a1b295c6/500x500-000000-80-0-0.jpg

    @SerializedName("picture_medium")
    override val pictureMedium: String, // https://e-cdns-images.dzcdn.net/images/artist/0707267475580b1b82f4da20a1b295c6/250x250-000000-80-0-0.jpg

    @SerializedName("picture_small")
    override val pictureSmall: String, // https://e-cdns-images.dzcdn.net/images/artist/0707267475580b1b82f4da20a1b295c6/56x56-000000-80-0-0.jpg

    @SerializedName("picture_xl")
    override val pictureXl: String, // https://e-cdns-images.dzcdn.net/images/artist/0707267475580b1b82f4da20a1b295c6/1000x1000-000000-80-0-0.jpg

    @SerializedName("radio")
    override val radio: Boolean, // true

    @SerializedName("tracklist")
    override val tracklist: String, // https://api.deezer.com/artist/13/top?limit=50

    @SerializedName("type")
    override val type: String // artist
) : ArtistModel
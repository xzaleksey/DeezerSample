package com.alekseyvalyakin.deezersample.common.network.response

import com.alekseyvalyakin.deezersample.common.domain.models.ArtistInTrackModel
import com.google.gson.annotations.SerializedName

data class ArtistInTrackResponse(
    @SerializedName("id")
    override val id: String, // 27
    @SerializedName("name")
    override val name: String, // Daft Punk
    @SerializedName("tracklist")
    override val tracklist: String, // https://api.deezer.com/artist/27/top?limit=50
    @SerializedName("type")
    override val type: String // artist
) : ArtistInTrackModel
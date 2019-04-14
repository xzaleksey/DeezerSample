package com.alekseyvalyakin.deezersample.common.domain.models

import java.io.Serializable

interface ArtistInTrackModel : Serializable {
    val id: String
    val name: String
    val tracklist: String
    val type: String
}
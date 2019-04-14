package com.alekseyvalyakin.deezersample.common.domain.models

import java.io.Serializable

interface TrackModel : Serializable {
    val artist: ArtistInTrackModel
    val duration: String
    val explicitContentCover: Int
    val explicitContentLyrics: Int
    val explicitLyrics: Boolean
    val id: String
    val link: String
    val preview: String
    val rank: String
    val readable: Boolean
    val title: String
    val titleShort: String
    val titleVersion: String
    val type: String
}
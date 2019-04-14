package com.alekseyvalyakin.deezersample.common.network.response

import com.alekseyvalyakin.deezersample.common.domain.models.TrackModel
import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("artist")
    override val artist: ArtistInTrackResponse,
    @SerializedName("duration")
    override val duration: String, // 600
    @SerializedName("explicit_content_cover")
    override val explicitContentCover: Int, // 0
    @SerializedName("explicit_content_lyrics")
    override val explicitContentLyrics: Int, // 0
    @SerializedName("explicit_lyrics")
    override val explicitLyrics: Boolean, // false
    @SerializedName("id")
    override val id: String, // 3135566
    @SerializedName("link")
    override val link: String, // https://www.deezer.com/track/3135566
    @SerializedName("preview")
    override val preview: String, // https://cdns-preview-d.dzcdn.net/stream/c-ddf495316e2afbe4327d9a6e17840a69-5.mp3
    @SerializedName("rank")
    override val rank: String, // 541921
    @SerializedName("readable")
    override val readable: Boolean, // true
    @SerializedName("title")
    override val title: String, // Too Long
    @SerializedName("title_short")
    override val titleShort: String, // Too Long
    @SerializedName("title_version")
    override val titleVersion: String,
    @SerializedName("type")
    override val type: String // track
) : TrackModel
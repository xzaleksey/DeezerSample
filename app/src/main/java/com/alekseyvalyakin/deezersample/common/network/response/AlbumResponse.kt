package com.alekseyvalyakin.deezersample.common.network.response

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class AlbumResponse(
    @SerializedName("artist")
    override val artist: ArtistResponse?,
    @SerializedName("available")
    override val available: Boolean, // true
    @SerializedName("contributors")
    override val contributors: List<Contributor>?,
    @SerializedName("cover")
    override val cover: String, // https://api.deezer.com/album/302127/image
    @SerializedName("cover_big")
    override val coverBig: String, // https://cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/500x500-000000-80-0-0.jpg
    @SerializedName("cover_medium")
    override val coverMedium: String, // https://cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/250x250-000000-80-0-0.jpg
    @SerializedName("cover_small")
    override val coverSmall: String, // https://cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/56x56-000000-80-0-0.jpg
    @SerializedName("cover_xl")
    override val coverXl: String, // https://cdns-images.dzcdn.net/images/cover/2e018122cb56986277102d2041a592c8/1000x1000-000000-80-0-0.jpg
    @SerializedName("duration")
    override val duration: Int, // 3660
    @SerializedName("explicit_content_cover")
    override val explicitContentCover: Int, // 0
    @SerializedName("explicit_content_lyrics")
    override val explicitContentLyrics: Int, // 7
    @SerializedName("explicit_lyrics")
    override val explicitLyrics: Boolean, // false
    @SerializedName("fans")
    override val fans: Int, // 193737
    @SerializedName("genre_id")
    override val genreId: Int, // 113
    @SerializedName("genres")
    override val genres: GenresResponse,
    @SerializedName("id")
    override val id: String, // 302127
    @SerializedName("label")
    override val label: String, // Parlophone France
    @SerializedName("link")
    override val link: String, // https://www.deezer.com/album/302127
    @SerializedName("nb_tracks")
    override val nbTracks: Int, // 14
    @SerializedName("rating")
    override val rating: Int, // 0
    @SerializedName("record_type")
    override val recordType: String, // album
    @SerializedName("release_date")
    override val releaseDate: String, // 2001-03-07
    @SerializedName("share")
    override val share: String, // https://www.deezer.com/album/302127?utm_source=deezer&utm_content=album-302127&utm_term=0_1555163830&utm_medium=web
    @SerializedName("title")
    override val title: String, // Discovery
    @SerializedName("tracklist")
    override val tracklist: String, // https://api.deezer.com/album/302127/tracks
    @SerializedName("tracks")
    override val tracks: Tracks?,
    @SerializedName("type")
    override val type: String, // album
    @SerializedName("upc")
    override val upc: String // 724384960650
) : AlbumModel, Serializable


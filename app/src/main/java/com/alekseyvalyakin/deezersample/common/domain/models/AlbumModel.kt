package com.alekseyvalyakin.deezersample.common.domain.models

import com.alekseyvalyakin.deezersample.common.network.response.Contributor
import com.alekseyvalyakin.deezersample.common.network.response.GenresResponse
import java.io.Serializable


//Database models could implement same interfaces
// common could become a module or several modules
interface AlbumModel : Serializable {
    val artist: ArtistModel?
    val available: Boolean
    val contributors: List<Contributor>?
    val cover: String? //120
    val coverBig: String? //500
    val coverMedium: String?//250
    val coverSmall: String? //56
    val coverXl: String? //1000
    val duration: Int
    val explicitContentCover: Int
    val explicitContentLyrics: Int
    val explicitLyrics: Boolean
    val fans: Int
    val genreId: Int
    val genres: GenresResponse?
    val id: String
    val label: String
    val link: String
    val nbTracks: Int
    val rating: Int
    val recordType: String
    val releaseDate: String
    val share: String
    val title: String
    val tracklist: String
    val tracks: TracksModel?
    val type: String
    val upc: String

    companion object {
        fun getAlbumQualityChooser(size: Int): (album: AlbumModel) -> String? {
            return { it.getBestCover(size) }
        }
    }

    fun getBestCover(size: Int): String? {
        val closestSize = ImageSize.getClosestSize(size)
        return when (closestSize) {
            ImageSize.BIG -> coverBig
            ImageSize.MEDIUM -> coverMedium
            ImageSize.XL -> coverXl
            ImageSize.SMALL -> cover
            else -> coverSmall
        }
    }
}
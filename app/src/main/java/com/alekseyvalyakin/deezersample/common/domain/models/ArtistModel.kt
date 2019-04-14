package com.alekseyvalyakin.deezersample.common.domain.models

import java.io.Serializable


interface ArtistModel : Serializable {
    val id: String

    val link: String

    val name: String

    val nbAlbum: Int

    val nbFan: Int

    val picture: String

    val pictureBig: String

    val pictureMedium: String

    val pictureSmall: String

    val pictureXl: String

    val radio: Boolean

    val tracklist: String

    val type: String

    companion object {
        fun getQualityChooser(size: Int): (album: ArtistModel) -> String? {
            return { it.getBestCover(size) }
        }
    }

    fun getBestCover(size: Int): String? {
        val closestSize = ImageSize.getClosestSize(size)
        return when (closestSize) {
            ImageSize.BIG -> pictureBig
            ImageSize.MEDIUM -> pictureMedium
            ImageSize.XL -> pictureXl
            ImageSize.SMALL -> picture
            else -> pictureSmall
        }
    }
}
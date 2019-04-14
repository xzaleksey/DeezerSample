package com.alekseyvalyakin.deezersample.common.repository

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import io.reactivex.Single

interface DeezerRepository {
    fun searchArtist(query: String): Single<List<ArtistModel>>

    fun getArtistAlbums(artistId: String): Single<List<AlbumModel>>
    fun getAlbumModel(albumId: String): Single<AlbumModel>
}
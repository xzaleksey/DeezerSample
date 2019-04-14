package com.alekseyvalyakin.deezersample.common.repository

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.common.network.api.DeezerApi
import io.reactivex.Scheduler
import io.reactivex.Single

//Here could be caching to DbApi
class DeezerRepositoryImpl(
    private val ioScheduler: Scheduler,
    private val deezerApi: DeezerApi
) : DeezerRepository {

    override fun searchArtist(query: String): Single<List<ArtistModel>> {
        return deezerApi
            .searchArtist(query)
            .map { it.data as List<ArtistModel> }
            .subscribeOn(ioScheduler)
    }

    override fun getArtistAlbums(artistId: String): Single<List<AlbumModel>> {
        return deezerApi
            .getArtistAlbums(artistId)
            .map { it.data as List<AlbumModel> }
            .subscribeOn(ioScheduler)
    }

    override fun getAlbumModel(albumId: String): Single<AlbumModel> {
        return deezerApi.getAlbum(albumId)
            .cast(AlbumModel::class.java)
            .subscribeOn(ioScheduler)
    }

}
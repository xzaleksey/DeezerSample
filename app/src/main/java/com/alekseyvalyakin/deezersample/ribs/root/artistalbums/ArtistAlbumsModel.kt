package com.alekseyvalyakin.deezersample.ribs.root.artistalbums

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import java.io.Serializable

class ArtistAlbumsModel(
    val artistResponse: List<AlbumModel> = emptyList(),
    val stateArtist: ArtistAlbumState = ArtistAlbumState.LOADING
) : Serializable

enum class ArtistAlbumState {
    SUCCESS,
    FAIL,
    LOADING
}
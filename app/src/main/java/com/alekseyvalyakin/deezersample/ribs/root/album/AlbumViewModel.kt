package com.alekseyvalyakin.deezersample.ribs.root.album

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel

class AlbumViewModel(
    val albumModel: AlbumModel? = null,
    val state: State = State.LOADING
) {

    enum class State {
        SUCCESS,
        FAILED,
        LOADING
    }
}
package com.alekseyvalyakin.deezersample.ribs.root.artistalbums.dependencies

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel

interface AlbumChooseListener {
    fun onAlbumSelected(albumModel: AlbumModel)
}
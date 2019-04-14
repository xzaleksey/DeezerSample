package com.alekseyvalyakin.deezersample.ribs.root.artistalbums.recycler

import com.alekseyvalyakin.deezersample.common.domain.models.AlbumModel
import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.common.image.ImageProvider
import com.alekseyvalyakin.deezersample.common.view.recyclerview.image.FlexibleSquareImageWithTwoLineTextModel

class ArtistAlbumFlexibleViewModel(
    imageProvider: ImageProvider,
    val albumModel: AlbumModel,
    val artistModel: ArtistModel
) : FlexibleSquareImageWithTwoLineTextModel(
    albumModel.title,
    artistModel.name,
    imageProvider,
    albumModel.id
) {

    override fun getLayoutRes(): Int {
        return ArtistAlbumFlexibleTypes.ALBUM
    }
}

package com.alekseyvalyakin.deezersample.ribs.root.artistsearch.recycler

import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.common.image.ImageProvider
import com.alekseyvalyakin.deezersample.common.view.recyclerview.image.FlexibleAvatarWithOneLineTextModel

class ArtistFlexibleViewModel(
    imageProvider: ImageProvider,
    val artistModel: ArtistModel
) : FlexibleAvatarWithOneLineTextModel(
    artistModel.name,
    imageProvider,
    artistModel.id
) {

    override fun getLayoutRes(): Int {
        return ArtistSearchFlexibleTypes.ARTIST
    }

}

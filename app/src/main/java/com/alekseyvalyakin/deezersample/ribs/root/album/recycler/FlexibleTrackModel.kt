package com.alekseyvalyakin.deezersample.ribs.root.album.recycler

import com.alekseyvalyakin.deezersample.common.domain.models.TrackModel
import com.alekseyvalyakin.deezersample.common.view.recyclerview.text.FlexibleThreeTextModel

class FlexibleTrackModel(
    val trackModel: TrackModel,
    number: String
) : FlexibleThreeTextModel(
    trackModel.title,
    trackModel.artist.name,
    number,
    trackModel.id
) {
    override fun getLayoutRes() = AlbumFlexibleTypes.TRACK
}
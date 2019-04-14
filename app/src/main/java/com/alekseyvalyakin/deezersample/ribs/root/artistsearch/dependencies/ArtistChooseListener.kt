package com.alekseyvalyakin.deezersample.ribs.root.artistsearch.dependencies

import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel

interface ArtistChooseListener {
    fun onArtistSelected(artistModel: ArtistModel)
}
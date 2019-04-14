package com.alekseyvalyakin.deezersample.ribs.root.artistsearch

import com.alekseyvalyakin.deezersample.common.domain.models.ArtistModel
import com.alekseyvalyakin.deezersample.common.utils.strings.EMPTY_STRING
import java.io.Serializable

data class ArtistSearchModel(
    val query: String = EMPTY_STRING,
    val artistResponse: List<ArtistModel> = emptyList(),
    val state: ArtistSearchState = ArtistSearchState.LOADING
) : Serializable

enum class ArtistSearchState {
    SUCCESS,
    FAIL,
    LOADING
}
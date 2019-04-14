package com.alekseyvalyakin.deezersample.common.network.api

object ApiParams {
    //params for path
    const val PARAM_ID = "id"

    //Api paths
    const val PATH_SEARCH_ARTIST = "/search/artist"
    const val PATH_ARTIST_ALBUM = "/artist/{$PARAM_ID}/albums"
    const val PATH_ALBUM = "/album/{$PARAM_ID}"

    //params for api
    const val PARAM_QUERY = "q"

}
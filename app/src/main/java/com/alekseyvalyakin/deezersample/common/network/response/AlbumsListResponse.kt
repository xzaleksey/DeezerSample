package com.alekseyvalyakin.deezersample.common.network.response

import com.google.gson.annotations.SerializedName

class AlbumsListResponse(
    @SerializedName("data")
    val data: List<AlbumResponse> = emptyList()
)
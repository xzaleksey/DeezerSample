package com.alekseyvalyakin.deezersample.common.network.response

import com.google.gson.annotations.SerializedName

class ArtistListResponse(
    @SerializedName("data")
    val data: List<ArtistResponse> = emptyList()
)
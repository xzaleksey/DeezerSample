package com.alekseyvalyakin.deezersample.common.network.response

import com.alekseyvalyakin.deezersample.common.domain.models.TracksModel
import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("data")
    override val data: List<TrackResponse> = emptyList()
) : TracksModel
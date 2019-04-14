package com.alekseyvalyakin.deezersample.common.network.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

//TODO change to model
data class GenresResponse(
    @SerializedName("data")
    val data: List<GenreResponse>
) : Serializable
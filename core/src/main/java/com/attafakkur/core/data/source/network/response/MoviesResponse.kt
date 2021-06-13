package com.attafakkur.core.data.source.network.response

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results")
    val results: List<MovieResponse>
)
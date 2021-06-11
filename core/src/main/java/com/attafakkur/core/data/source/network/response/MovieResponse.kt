package com.attafakkur.core.data.source.network.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("release_date")
        val release: String,
        @SerializedName("poster_path")
        val poster: String,
        @SerializedName("backdrop_path")
        val backDrop: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("vote_average")
        val vote: String,
)
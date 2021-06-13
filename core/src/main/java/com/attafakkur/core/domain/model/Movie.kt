package com.attafakkur.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val release: String,
    val poster: String,
    val backDrop: String,
    val overview: String,
    val vote: String,
    val isFavorite: Boolean
) : Parcelable
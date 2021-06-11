package com.attafakkur.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class MoviesEntity(
        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "id")
        val id: Int,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "release_date")
        val release: String,
        @ColumnInfo(name = "poster_path")
        val poster: String?,
        @ColumnInfo(name = "backdrop_path")
        val backDrop: String?,
        @ColumnInfo(name = "overview")
        val overview: String,
        @ColumnInfo(name = "vote")
        val vote: String,
        @ColumnInfo(name = "isFavorite")
        var isFavorite: Boolean
)
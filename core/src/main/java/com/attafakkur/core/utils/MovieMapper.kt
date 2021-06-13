package com.attafakkur.core.utils

import com.attafakkur.core.data.source.local.entity.MoviesEntity
import com.attafakkur.core.data.source.network.response.MovieResponse
import com.attafakkur.core.domain.model.Movie

object MovieMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MoviesEntity> {
        val movieList = ArrayList<MoviesEntity>()
        input.map {

            val movie = MoviesEntity(
                id = it.id,
                title = it.title,
                release = it.release,
                poster = it.poster,
                backDrop = it.backDrop,
                overview = it.overview,
                vote = it.vote,
                isFavorite = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MoviesEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                release = it.release,
                poster = it.poster ?: "",
                backDrop = it.backDrop ?: "",
                overview = it.overview,
                vote = it.vote,
                isFavorite = it.isFavorite
            )
        }

    fun mapDomainToEntity(input: Movie): MoviesEntity =
        MoviesEntity(
            id = input.id,
            title = input.title,
            release = input.release,
            poster = input.poster,
            backDrop = input.backDrop,
            overview = input.overview,
            vote = input.vote,
            isFavorite = input.isFavorite
        )

    fun mapEntityToDomain(input: MoviesEntity): Movie =
        Movie(
            id = input.id,
            title = input.title,
            release = input.release,
            poster = input.poster ?: "",
            backDrop = input.backDrop ?: "",
            overview = input.overview,
            vote = input.vote,
            isFavorite = input.isFavorite
        )

    fun mapResponseToEntity(input: MovieResponse): MoviesEntity =
        MoviesEntity(
            id = input.id,
            title = input.title,
            release = input.release,
            poster = input.poster,
            backDrop = input.backDrop,
            overview = input.overview,
            vote = input.vote,
            isFavorite = false
        )
}
package com.attafakkur.core.domain.usecase

import com.attafakkur.core.data.State
import com.attafakkur.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovies(): Flow<State<List<Movie>>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getDetailMovie(id: Int): Flow<State<Movie>>
    fun setFavoriteMovie(movie: Movie)

    suspend fun searchMovies(query: String): State<List<Movie>>
    fun deleteMovies()
}
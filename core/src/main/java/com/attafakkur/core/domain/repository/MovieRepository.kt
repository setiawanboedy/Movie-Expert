package com.attafakkur.core.domain.repository

import com.attafakkur.core.data.State
import com.attafakkur.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<State<List<Movie>>>
    fun getFavoriteMovies(): Flow<List<Movie>>
    fun getDetailMovie(id: Int): Flow<State<Movie>>
    fun setFavoriteMovie(movie: Movie)

    suspend fun searchMovies(query: String): State<List<Movie>>
    suspend fun insertMovie(movie: Movie)
    fun deleteMovies()
}
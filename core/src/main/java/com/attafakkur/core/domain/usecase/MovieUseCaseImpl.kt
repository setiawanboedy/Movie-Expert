package com.attafakkur.core.domain.usecase

import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.domain.repository.MovieRepository
import javax.inject.Inject

class MovieUseCaseImpl @Inject constructor(private val movieRepository: MovieRepository) :
    MovieUseCase {
    override fun getMovies() = movieRepository.getMovies()

    override fun getFavoriteMovies() = movieRepository.getFavoriteMovies()

    override fun getDetailMovie(id: Int) = movieRepository.getDetailMovie(id)

    override fun setFavoriteMovie(movie: Movie) = movieRepository.setFavoriteMovie(movie)

    override suspend fun searchMovies(query: String) = movieRepository.searchMovies(query)
    override fun deleteMovies() = movieRepository.deleteMovies()


}
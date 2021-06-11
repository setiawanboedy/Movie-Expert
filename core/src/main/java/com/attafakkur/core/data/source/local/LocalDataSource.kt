package com.attafakkur.core.data.source.local

import com.attafakkur.core.data.source.local.entity.MoviesEntity
import com.attafakkur.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val movieDao: MovieDao) {
    fun getAllMovies(): Flow<List<MoviesEntity>> = movieDao.getAllMovies()
    fun getFavoriteMovies(): Flow<List<MoviesEntity>> = movieDao.getFavoriteMovies()
    fun getMovieById(id: Int): Flow<MoviesEntity>? = movieDao.getMovieById(id)

    suspend fun insertAllMovies(movies: List<MoviesEntity>) = movieDao.insertAllMovies(movies)

    suspend fun insertMovie(movie: MoviesEntity) = movieDao.insertMovie(movie)
    fun editMovie(movie: MoviesEntity) = movieDao.updateMovie(movie)
    fun deleteAllMovies() = movieDao.deleteAllMovie()
}
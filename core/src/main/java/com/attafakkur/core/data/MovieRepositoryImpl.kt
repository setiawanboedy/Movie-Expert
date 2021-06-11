package com.attafakkur.core.data

import android.util.Log
import com.attafakkur.core.data.source.local.LocalDataSource
import com.attafakkur.core.data.source.local.entity.MoviesEntity
import com.attafakkur.core.data.source.network.RemoteDataSource
import com.attafakkur.core.data.source.network.api.ApiResponse
import com.attafakkur.core.data.source.network.response.MovieResponse
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.domain.repository.MovieRepository
import com.attafakkur.core.utils.AppExecutors
import com.attafakkur.core.utils.MovieMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class MovieRepositoryImpl @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
) : MovieRepository {
    override fun getMovies(): Flow<State<List<Movie>>> =
            object : NetworkResourceBound<List<Movie>, List<MovieResponse>>() {
                override fun loadFromDB(): Flow<List<Movie>> {
                    return localDataSource.getAllMovies().map {
                        MovieMapper.mapEntitiesToDomain(it)
                    }
                }

                override fun shouldFetch(data: List<Movie>?): Boolean {

                    Log.d("movie", "repository : $data")
                    return data?.isEmpty() == true || data == null
                }

                override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                        remoteDataSource.getAllMovies()

                override suspend fun saveCallResult(data: List<MovieResponse>) {
                    val movieList = MovieMapper.mapResponsesToEntities(data)
                    localDataSource.insertAllMovies(movieList)

                }
            }.asFlow() as Flow<State<List<Movie>>>


    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map {
            MovieMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getDetailMovie(id: Int): Flow<State<Movie>> =
            object : NetworkResourceBound<Movie, MovieResponse>() {
                override fun loadFromDB(): Flow<Movie?>? {
                    return localDataSource.getMovieById(id)?.map { value: MoviesEntity? ->
                        if (value == null) {
                            return@map null
                        } else {
                            return@map MovieMapper.mapEntityToDomain(value)
                        }
                    }
                }

                override fun shouldFetch(data: Movie?): Boolean {
                    return data?.overview == "" || data == null
                }

                override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                        remoteDataSource.getDetailMovie(id)

                override suspend fun saveCallResult(data: MovieResponse) {
                    val movieDetail = MovieMapper.mapResponseToEntity(data)
                    localDataSource.insertMovie(movieDetail)
                }
            }.asFlow() as Flow<State<Movie>>

    override fun setFavoriteMovie(movie: Movie) {
        val movieEntity = MovieMapper.mapDomainToEntity(movie)
        movieEntity.isFavorite = !movieEntity.isFavorite
        appExecutors.diskIO().execute { localDataSource.editMovie(movieEntity) }
    }

    override suspend fun searchMovies(query: String): State<List<Movie>> {
        return when (val response = remoteDataSource.searchMovies(query).first()) {
            is ApiResponse.Success -> {
                val movieEntities = MovieMapper.mapResponsesToEntities(response.data)
                val movies = MovieMapper.mapEntitiesToDomain(movieEntities)
                State.Success(movies)
            }
            is ApiResponse.Error -> {
                State.Error(response.errorMessage, null)
            }
            is ApiResponse.Empty -> {
                State.Empty(response.toString(), null)
            }
        }
    }

    override suspend fun insertMovie(movie: Movie) {
        val movieEntity = MovieMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.editMovie(movieEntity) }
    }

    override fun deleteMovies() {
        appExecutors.diskIO().execute { localDataSource.deleteAllMovies() }
    }
}
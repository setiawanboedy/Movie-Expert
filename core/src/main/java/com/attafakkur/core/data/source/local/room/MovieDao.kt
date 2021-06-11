package com.attafakkur.core.data.source.local.room

import androidx.room.*
import com.attafakkur.core.data.source.local.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorite")
    fun getAllMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM favorite WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MoviesEntity>>

    @Query("SELECT * FROM favorite WHERE id = :id")
    fun getMovieById(id: Int): Flow<MoviesEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movie: List<MoviesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MoviesEntity)

    @Query("DELETE FROM favorite")
    fun deleteAllMovie()

    @Update
    fun updateMovie(movie: MoviesEntity)
}
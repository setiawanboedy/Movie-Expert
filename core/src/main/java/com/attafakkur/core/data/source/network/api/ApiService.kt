package com.attafakkur.core.data.source.network.api

import com.attafakkur.core.data.source.network.response.MovieResponse
import com.attafakkur.core.data.source.network.response.MoviesResponse
import com.attafakkur.core.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("discover/movie?api_key=$API_KEY")
    suspend fun getMoviesList(): MoviesResponse

    @GET("movie/{movie_id}?api_key=$API_KEY")
    suspend fun getMovieDetail(
            @Path("movie_id") movie_id: String
    ): MovieResponse

    @GET("search/movie?api_key=$API_KEY")
    suspend fun getSearchMovieDb(
            @Query("query") search: String
    ): MoviesResponse
}
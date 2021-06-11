package com.attafakkur.movieexpert.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.attafakkur.core.data.State
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.domain.usecase.MovieUseCase

class SearchViewModel @ViewModelInject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {
    private var movies = MutableLiveData<State<List<Movie>>>()
    val movie: LiveData<State<List<Movie>>> get() = movies

    suspend fun searchMovies(query: String) {
        movies.value = movieUseCase.searchMovies(query)
    }
}
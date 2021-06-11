package com.attafakkur.movieexpert.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.attafakkur.core.domain.model.Movie
import com.attafakkur.core.domain.usecase.MovieUseCase

class DetailViewModel @ViewModelInject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {
    fun getDetailMovie(id: Int) = movieUseCase.getDetailMovie(id).asLiveData()
    fun setFavoriteMovie(movie: Movie) = movieUseCase.setFavoriteMovie(movie)
}
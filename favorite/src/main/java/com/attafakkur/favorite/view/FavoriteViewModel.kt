package com.attafakkur.favorite.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.attafakkur.core.domain.usecase.MovieUseCase

class FavoriteViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {
    val favMovies = movieUseCase.getFavoriteMovies().asLiveData()
    fun deleteAll() = movieUseCase.deleteMovies()
}
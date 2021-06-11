package com.attafakkur.movieexpert.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.attafakkur.core.domain.usecase.MovieUseCaseImpl

class HomeViewModel @ViewModelInject constructor(movieUseCase: MovieUseCaseImpl) : ViewModel() {
    val movie = movieUseCase.getMovies().asLiveData()
}
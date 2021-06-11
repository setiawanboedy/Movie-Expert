package com.attafakkur.core.di

import com.attafakkur.core.data.MovieRepositoryImpl
import com.attafakkur.core.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}
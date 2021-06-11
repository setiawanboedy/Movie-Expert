package com.attafakkur.core.di

import android.content.Context
import androidx.room.Room
import com.attafakkur.core.data.source.local.room.MovieDao
import com.attafakkur.core.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
            @ApplicationContext context: Context
    ): MovieDatabase = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "favorite"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()
}
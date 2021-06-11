package com.attafakkur.core.di

import android.content.Context
import androidx.room.Room
import com.attafakkur.core.BuildConfig
import com.attafakkur.core.data.source.local.room.MovieDao
import com.attafakkur.core.data.source.local.room.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        val passPhrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASSPHRASE.toCharArray())
        val factory = SupportFactory(passPhrase)

        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "favorite"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()
}
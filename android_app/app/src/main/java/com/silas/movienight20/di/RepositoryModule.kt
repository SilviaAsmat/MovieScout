package com.silas.movienight20.di

import com.silas.movienight20.data.MovieDatabaseNetworkService
import com.silas.movienight20.data.MovieRepositoryImpl
import com.silas.movienight20.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideMoviesRepository(
        repo: MovieRepositoryImpl
    ): MoviesRepository
}

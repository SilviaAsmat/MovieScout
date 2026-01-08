package com.silas.movienight20.di
import androidx.room.Room
import android.content.Context
import com.silas.movienight20.data.room.MovieScoutDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    companion object {
        private const val DATABASE_NAME = "movie_scout_database"
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): MovieScoutDatabase {
        return Room.databaseBuilder(context,
            MovieScoutDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

}
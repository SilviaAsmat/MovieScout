//package com.example.movienight20.data
//
//import android.content.Context
//import androidx.room.Room
//import com.example.movienight20.data.room.MovieInfoBasicDao
//import com.example.movienight20.data.room.MovieScoutDatabase
//import com.example.movienight20.data.room.RemoteKeysDao
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class SingletonModule {
//    @Singleton
//    @Provides
//    fun provideRetrofitInstance(): MovieDatabaseNetworkService =
//        Retrofit.Builder()
//            .baseUrl("https://api.themoviedb.org/3/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(MovieDatabaseNetworkService::class.java)
//
//    @Singleton
//    @Provides
//    fun provideMovieDatabase(@ApplicationContext context: Context): MovieScoutDatabase =
//        Room
//            .databaseBuilder(context, MovieScoutDatabase::class.java, "movies_database")
//            .build()
//
//    @Singleton
//    @Provides
//    fun provideMoviesDao(moviesDatabase: MovieScoutDatabase): MovieInfoBasicDao = moviesDatabase.movieInfoBasic()
//
//    @Singleton
//    @Provides
//    fun provideRemoteKeysDao(moviesDatabase: MovieScoutDatabase): RemoteKeysDao = moviesDatabase.getRemoteKeysDao()
//}
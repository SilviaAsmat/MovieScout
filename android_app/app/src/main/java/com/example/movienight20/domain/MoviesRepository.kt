package com.example.movienight20.domain

import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    suspend fun getMovies(): List<PopularMoviesInfo>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMovieCredits(movieId: Int): MovieCredits
    suspend fun getPeopleMovieCredits(personId: Int): PeopleMovieCredits
    suspend fun getPeopleDetails(personId: Int): PeopleDetails
    suspend fun getNowPlaying(): List<PopularMoviesInfo>
    suspend fun storeDataInCache(movie: MovieDetails)
    fun getRecentlyViewed(): Flow<List<MovieInfoBasic>>
}
package com.example.movienight20.domain

interface MoviesRepository {
    suspend fun getMovies(): List<MovieInfo>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
}
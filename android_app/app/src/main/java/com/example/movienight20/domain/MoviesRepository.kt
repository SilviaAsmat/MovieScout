package com.example.movienight20.domain

interface MoviesRepository {
    suspend fun getMovies(): List<MovieInfo>
}
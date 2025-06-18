package com.example.movienight20.domain


data class PopularMoviesInfo (
    val id: Int,
    val title: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val rating: String
)
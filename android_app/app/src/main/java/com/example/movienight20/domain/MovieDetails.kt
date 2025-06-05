package com.example.movienight20.domain

data class MovieDetails (
    val id: Int,
    val title: String,
    val backdropPath: String,
    val overview: String,
    val runtime: Int,
    val status: String,
    val genres: List<Genre>
)

data class Genre(
    val id: Int?,
    val title: String?
)
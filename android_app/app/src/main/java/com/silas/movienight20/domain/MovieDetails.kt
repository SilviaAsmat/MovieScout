package com.silas.movienight20.domain

data class MovieDetails (
    val id: Int,
    val title: String,
    val backdropPath: String,
    val posterPath: String,
    val overview: String,
    val runtime: Int,
    val status: String,
    val genres: List<Genre>,
    val releaseDate: String,
    val voteAvg: Number,
    val voteCount: Int,
    val tagline: String,
)

data class Genre(
    val id: Int?,
    val title: String?
)
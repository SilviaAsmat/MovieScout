package com.example.movienight20.domain

data class MovieCredits (
    val movieId: Int,
    val cast: List<Cast>
)

data class Cast (
    val castId: Int,
    val name: String?,
    val picturePath: String?,
    val character: String?
)

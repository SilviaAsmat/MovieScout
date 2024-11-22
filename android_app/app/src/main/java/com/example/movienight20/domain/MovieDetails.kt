package com.example.movienight20.domain

import com.google.gson.annotations.SerializedName

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
    @SerializedName("id")
    val id: Int?,
    @SerializedName("genre")
    val title: String?
)
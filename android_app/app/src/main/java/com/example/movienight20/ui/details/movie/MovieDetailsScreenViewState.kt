package com.example.movienight20.ui.details.movie

import com.example.movienight20.domain.Cast
import com.example.movienight20.domain.Genre

data class MovieDetailsScreenViewState(
    val id: Int,
    val title: String,
    val backdropPath: String,
    val overview: String,
    val runtime: Int,
    val status: String,
    val genres: List<Genre>,
    val releaseDate: String,
    val voteAvg: Number,
    val voteCount: Int,
    val tagline: String,
    val cast: List<Cast>
) {
    companion object {
        val NONE = MovieDetailsScreenViewState(
            id = 0,
            title = "",
            backdropPath = "",
            overview = "",
            runtime = 0,
            status = "",
            genres = listOf(),
            releaseDate = "",
            voteAvg = 0,
            voteCount = 1,
            tagline = "",
            cast = listOf()
        )
    }
}
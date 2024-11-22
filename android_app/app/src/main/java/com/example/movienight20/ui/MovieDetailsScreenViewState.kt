package com.example.movienight20.ui

import com.example.movienight20.domain.Genre

data class MovieDetailsScreenViewState(
    val id: Int,
    val title: String,
    val backdropPath: String,
    val overview: String,
    val runtime: Int,
    val status: String,
    val genres: String // Actually of type List<Genre>, String for demo purpose
)
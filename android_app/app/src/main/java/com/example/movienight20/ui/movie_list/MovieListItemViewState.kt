package com.example.movienight20.ui.movie_list

// TODO add immutable annotation
data class MovieListItemViewState (
    val id: Int,
    val title: String,
    val url: String,
    val year: String,
    val rating: String
)
package com.example.movienight20.ui.search

import androidx.compose.runtime.Immutable

@Immutable
sealed class MovieSearchViewState {
    data class Data (
        val id: Int,
        val title: String,
        val backdropPath: String,
        val posterPath: String,
        val releaseDate: String,
        val rating: String
    )
    data object Loading: MovieSearchViewState()
}
package com.example.movienight20.ui.movie_list

import androidx.compose.runtime.Immutable

@Immutable
sealed class MovieListItemViewState() {
    data class Data(
        val id: Int,
        val title: String,
        val url: String,
        val year: String,
        val rating: String
    )
    data object Loading : MovieListItemViewState()
}
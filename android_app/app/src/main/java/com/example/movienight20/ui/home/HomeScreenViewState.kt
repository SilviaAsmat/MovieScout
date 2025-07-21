package com.example.movienight20.ui.home

import androidx.compose.runtime.Immutable
import com.example.movienight20.ui.MovieCardInfoViewState

@Immutable
class HomeScreenViewState(
    val popMoviesInfo: List<MovieCardInfoViewState>,
    val nowPlayingMoviesInfo: List<MovieCardInfoViewState>,
    val upcomingInfo: List<MovieCardInfoViewState>,
    val topRatedInfo: List<MovieCardInfoViewState>,
) {
    companion object {
        val NONE = HomeScreenViewState(
            popMoviesInfo = listOf(),
            nowPlayingMoviesInfo = listOf(),
            upcomingInfo = listOf(),
            topRatedInfo = listOf()
        )
    }
}

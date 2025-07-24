package com.example.movienight20.ui.home

import androidx.compose.runtime.Immutable
import com.example.movienight20.ui.MovieCardInfoViewState
import com.example.movienight20.ui.recently_viewed.RecentlyViewedViewState

@Immutable
sealed class HomeScreenViewState() {
    data class Data(
        val popMoviesInfo: List<MovieCardInfoViewState>,
        val nowPlayingMoviesInfo: List<MovieCardInfoViewState>,
        val upcomingInfo: List<MovieCardInfoViewState>,
        val topRatedInfo: List<MovieCardInfoViewState>,
    ) : HomeScreenViewState()

    data object Loading : HomeScreenViewState()
}

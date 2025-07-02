package com.example.movienight20.ui.home

import androidx.compose.runtime.Immutable
import com.example.movienight20.domain.PopularMoviesInfo

@Immutable
class HomeScreenViewState(
    val popMoviesInfo: List<PopularMoviesInfo>,
    val nowPlayingMoviesInfo: List<PopularMoviesInfo>,
) {
    companion object {
        val NONE = HomeScreenViewState(
            popMoviesInfo = listOf(),
            nowPlayingMoviesInfo = listOf(),
        )
    }
}

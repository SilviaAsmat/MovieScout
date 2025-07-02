package com.example.movienight20.ui

import androidx.compose.runtime.Immutable
import com.example.movienight20.domain.PopularMoviesInfo

@Immutable
class MainScreenViewState(
    val popMoviesInfo: List<PopularMoviesInfo>,
    val nowPlayingMoviesInfo: List<PopularMoviesInfo>,
) {
    companion object {
        val NONE = MainScreenViewState(
            popMoviesInfo = listOf(),
            nowPlayingMoviesInfo = listOf(),
        )
    }
}

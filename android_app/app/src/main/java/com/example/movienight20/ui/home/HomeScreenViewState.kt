package com.example.movienight20.ui.home

import androidx.compose.runtime.Immutable
import com.example.movienight20.domain.MoviesCollectionInfo

@Immutable
class HomeScreenViewState(
    val popMoviesInfo: List<MoviesCollectionInfo>,
    val nowPlayingMoviesInfo: List<MoviesCollectionInfo>,
) {
    companion object {
        val NONE = HomeScreenViewState(
            popMoviesInfo = listOf(),
            nowPlayingMoviesInfo = listOf(),
        )
    }
}

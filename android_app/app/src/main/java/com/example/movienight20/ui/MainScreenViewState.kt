package com.example.movienight20.ui

import androidx.compose.runtime.Immutable
import com.example.movienight20.domain.MovieInfoBasic
import com.example.movienight20.domain.PopularMoviesInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
class MainScreenViewState(
    val popMoviesInfo: List<PopularMoviesInfo>,
    val nowPlayingMoviesInfo: List<PopularMoviesInfo>,
    val recentlyViewedInfo: List<MovieInfoBasic>,

) {
    companion object {
        val NONE = MainScreenViewState(
            popMoviesInfo = listOf(),
            nowPlayingMoviesInfo = listOf(),
            recentlyViewedInfo = listOf()
        )
    }
}

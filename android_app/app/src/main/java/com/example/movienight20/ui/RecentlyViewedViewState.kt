package com.example.movienight20.ui

import androidx.compose.runtime.Immutable
import com.example.movienight20.domain.MovieInfoBasic;


@Immutable
class RecentlyViewedViewState(
    val recentlyViewedInfo: List<MovieInfoBasic>
) {
    companion object {
        val NONE = RecentlyViewedViewState(
            recentlyViewedInfo = emptyList<MovieInfoBasic>()
        )
    }
}

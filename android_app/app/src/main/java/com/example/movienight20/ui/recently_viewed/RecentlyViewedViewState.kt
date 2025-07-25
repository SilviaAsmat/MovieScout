package com.example.movienight20.ui.recently_viewed

import androidx.compose.runtime.Immutable
import com.example.movienight20.ui.search.ResultItemViewState

@Immutable
sealed class RecentlyViewedViewState {
    data class Data(val cards: List<ResultItemViewState>): RecentlyViewedViewState()
    data object Loading : RecentlyViewedViewState()
    data object Empty : RecentlyViewedViewState()
}
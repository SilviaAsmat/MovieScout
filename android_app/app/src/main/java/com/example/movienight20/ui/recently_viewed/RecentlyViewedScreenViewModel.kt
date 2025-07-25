package com.example.movienight20.ui.recently_viewed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movienight20.domain.MovieInfoBasic
import com.example.movienight20.domain.MoviesRepository
import com.example.movienight20.ui.MovieCardInfoViewState
import com.example.movienight20.ui.movie_list.MovieListItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyViewedScreenViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
) : ViewModel() {
    private val _recentsScreenViewState =
        MutableStateFlow<RecentlyViewedScreenViewState>(RecentlyViewedScreenViewState.Loading)
    val recentsScreenViewState: StateFlow<RecentlyViewedScreenViewState> = _recentsScreenViewState
    init {
        viewModelScope.launch() {
            movieRepo.getRecentlyViewed().collectLatest { recentlyViewed ->
                updateRecentsViewState(recentlyViewed)
            }
        }
    }

    fun updateRecentsViewState(recentlyViewed: List<MovieInfoBasic>) {
        val recentViewState = if (recentlyViewed.isEmpty()) {
            RecentlyViewedScreenViewState.Empty
        } else {
            RecentlyViewedScreenViewState.Data(cards = recentlyViewed.map { recent ->
                MovieListItemViewState.Data(
                    id = recent.id,
                    title = recent.title,
                    url = "http://image.tmdb.org/t/p/w1280${recent.posterPath}",
                    year = recent.releaseDate,
                    rating = recent.rating

                )
            })
        }
        _recentsScreenViewState.update { recentViewState }
    }

}